package com.ky_proj.spjplugin.annotator

/**
 * Created on 2018/01/27.
 */

import com.intellij.lang.ASTNode
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.*
import com.ky_proj.spjplugin.setting.SpjSetting
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import com.ky_proj.spjplugin.util.SpjSettingProvider
import com.ky_proj.spjplugin.util.SpjTreeUtil

class SpjAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        // プロシージャ呼び出し
        if (element.node.elementType === SpjTypes.PROCEDURE_CALL) {
            annotateProcedure(element, holder)
        }

        // プロシージャ定義
        if (element.node.elementType === SpjTypes.PROCEDURE_DEF) {
            annotateProcedureName(element, holder)
        }

        // 関数呼び出し
        if (element.node.elementType === SpjTypes.FUNCTION) {
            annotateCallingFunction(element, holder)
        }

        // 変数or引数
        if (element.node.elementType === SpjTypes.VARIABLE || element.node.elementType === SpjTypes.ARGUMENT) {
            annotateVariable(element, holder)
        }

        // 引数ブロック
        if (element.node.elementType === SpjTypes.ARGUMENTS) {
            annotateArguments(element, holder)
        }

        // try
        if (element.node.elementType === SpjTypes.TRYBLOCK) {
            annotateTryBlock(element, holder)
        }
    }


    /**
     * プロシージャ呼び出し時のannotate
     * @param element エレメント
     * @param holder ホルダ
     */
    private fun annotateProcedure(element: PsiElement, holder: AnnotationHolder) {
        val procedureName = element?.text ?: return
        val project = element.project
        val defs = SpjProcedureProvider.findDefinitionInProject(project = project, name = procedureName, is_only_return = false)
        if (defs.size == 0) {
            // プロシージャが定義されていない
            holder.createErrorAnnotation(element, "Undefined Procedure")
        } else if (defs.size == 1) {
            val def = defs[0]
            val caller = PsiTreeUtil.getParentOfType(element, SpjCallingProcedure::class.java)
            // 呼び出し元の引数の個数
            if (caller != null) {
                annotateArgumentsCount(element, holder, def, caller)
            }

        }
    }

    // 定義済みプロシージャ呼び出しの時に引数の個数を数える
    private fun annotateArgumentsCount(element: PsiElement, holder: AnnotationHolder, def: SpjProcedureDef, caller: PsiElement) {
        val setting = SpjSetting(element.project)
        val neoVersion = setting.getNeoVersion()



        val arg_count_defined = getArgsCount(def)
        val arg_count_called  = getArgsCount(caller)

        // performループのときはOK
        // 定義元のargumentが０個で、呼び出し元のargumentの中にOPERが1つ以上ある
        if (arg_count_defined == 0 && is_contain_oper(caller)) {
            if (neoVersion >= 3.0) {
                holder.createWarningAnnotation(caller, "Perform loops are deprecated. Use 'for' or 'while' instead.")
            }
            return
        }


        if (arg_count_called > arg_count_defined) {

            // 呼び出し時の個数が定義より多かったらneo4でエラー、それ以前ならワーニング
            if (neoVersion < 4.0) {
                holder.createWarningAnnotation(caller, "Number of arguments is bigger than definition")
            } else {
                holder.createErrorAnnotation(caller, "Too many arguments")
            }


        } else if (arg_count_called < arg_count_defined) {

            // 呼び出し時の個数が定義より少なかったwarning
            holder.createWarningAnnotation(caller, "Number of arguments is smaller than definition")
        }
    }

    // プロシージャ定義
    private fun annotateProcedureName(element: PsiElement, holder: AnnotationHolder) {

        // NEO4以上でセミコロンが入っていたらアウト
        utilAnnotateInvalidCharacter(element, holder)

        // 定義なのに引数に関数が入っていたらアウトにする
        val invalid_children = PsiTreeUtil.findChildrenOfType(element, SpjCallingFunction::class.java)
        for (child in invalid_children) {
            holder.createErrorAnnotation(child, "Invalid argument inside procedure definitions")
        }
    }

    // 関数呼び出し
    private fun annotateCallingFunction(element: PsiElement, holder: AnnotationHolder) {
        val procedureName = element?.text ?: return

        val project = element.project
        val isEnhanceMode = SpjSettingProvider.isEnhanceMode(project)

        // 不正な文字を含んでいる
        utilAnnotateInvalidCharacter(element, holder)

        val defs = SpjProcedureProvider.findDefinitionInProject(project = project, name = procedureName, is_only_return = false)

        if (! isEnhanceMode) {
            // エンハンスモードじゃいのにユーザー定義プロシージャを関数形式で呼んでいる
            if (defs.size > 0) {
                holder.createErrorAnnotation(element, "Enable EnhanceMode to get values via 'return'")
            }
        }

        if (isEnhanceMode && defs.size == 1) {
            val def = defs.get(0)

            // returnを含んでいないのに関数形式で呼び出している
            val block = PsiTreeUtil.getParentOfType(def, SpjProcedureBlock::class.java)
            if (block != null && SpjTreeUtil.findChildByTokenType(block, TokenSet.create(SpjTypes.RETURN)).isEmpty()) {
                holder.createErrorAnnotation(element, "This procedure does not include 'return'")
            }

            // 引数の個数
            val caller = PsiTreeUtil.getParentOfType(element, SpjCallingFunction::class.java) ?: return
            annotateArgumentsCount(element, holder, def, caller)

        }


    }

    // 変数
    private fun annotateVariable(element: PsiElement, holder: AnnotationHolder) {
        val varname = element?.text ?: return

        if (varname.isNotEmpty() && varname.substring(0, 1) == "＄") {
            holder.createWarningAnnotation(element, "May be '$'")
        }
        // NEO4以上でセミコロンが入っていたらアウト
        utilAnnotateInvalidCharacter(element, holder)

    }

    // 引数ブロック
    private fun annotateArguments(element: PsiElement, holder: AnnotationHolder) {
        val setting = SpjSetting(element.project)
        val neo_version = setting.getNeoVersion()

        // neo3以前なら問題ない
        if (neo_version < 4.0) {
            return
        }

        // computeの時だけは例外として認める
        val command = element.prevSibling
        if (command != null && command.text == "compute") {
            return
        }

        val children = element.node.getChildren(TokenSet.create(SpjTypes.ARGS, SpjTypes.NUMBER, SpjTypes.CALLING_FUNCTION, SpjTypes.COMMA, SpjTypes.STRING, SpjTypes.ARGUMENTS, SpjTypes.RPAR, SpjTypes.OPER))
        var prev_child: ASTNode? = null

        // いきなりカンマが来てたらアウト
        if (children.size > 0 && children[0].elementType === SpjTypes.COMMA) {
            holder.createErrorAnnotation(element, "Argument cannot be null")
            return
        }

        for (child in children) {
            // カンマの後にカンマが続くか、arguments自体が終わってたらアウト
            if (prev_child != null &&
                    prev_child.elementType === SpjTypes.COMMA &&
                    (child.elementType === SpjTypes.COMMA || child.elementType === SpjTypes.RPAR)) {
                holder.createErrorAnnotation(element, "Argument cannot be null")
                break
            }

            // カンマなしで変数が2つ続いてたらアウト
            if (prev_child != null && isValidElementTypeForArgument(prev_child) && isValidElementTypeForArgument(child)) {
                holder.createErrorAnnotation(child.psi, "',' is required to separate arguments")
                break
            }

            prev_child = child
        }

    }

    private fun annotateTryBlock(element: PsiElement, holder: AnnotationHolder) {
        val setting = SpjSetting(element.project)
        val neo_version = setting.getNeoVersion()

        // neo3以前ならエラー
        if (neo_version < 4.0) {
            holder.createErrorAnnotation(element, "Try statement is not supported in this version.")
        }


    }

    private fun utilAnnotateInvalidCharacter(element: PsiElement, holder: AnnotationHolder) {
        val project = element.project
        val setting = SpjSetting(project)

        val name = element.text
        if (setting.getNeoVersion() < 4.0 || name.length < 2) {
            return
        }

        if (name.length < 2) {
            return
        }


        // NEO4以上でセミコロンが入っていたらアウト
        val invalid_characters = arrayOf(";", "+", "-", "*", "/", "=", "%")
        for (character in invalid_characters) {
            if (name.contains(character)) {
                holder.createErrorAnnotation(element, "Invalid Character '$character' included")
            }
        }


    }

    private fun isValidElementTypeForArgument(node: ASTNode): Boolean {
        val target = TokenSet.create(SpjTypes.ARGS, SpjTypes.CALLING_FUNCTION, SpjTypes.STRING, SpjTypes.ARGUMENTS)
        return target.contains(node.elementType)
    }


    private fun getArgsCount(element: PsiElement): Int {

        val target = TokenSet.create(SpjTypes.PROCEDURE_DEF, SpjTypes.CALLING_PROCEDURE, SpjTypes.CALLING_FUNCTION)
        if (!target.contains(element.node.elementType)) {
            return 0
        }

        val arg = element.node.findChildByType(SpjTypes.ARGUMENTS) ?: return 0

// 引数の個数を数える
        val arguments = arg.getChildren(TokenSet.create(SpjTypes.ARGS, SpjTypes.NUMBER, SpjTypes.CALLING_FUNCTION, SpjTypes.STRING, SpjTypes.ARGUMENTS))

        return arguments.size
    }

    private fun is_contain_oper(element: PsiElement): Boolean {

        val arg = element.node.findChildByType(SpjTypes.ARGUMENTS) ?: return false

        val target = TokenSet.create(SpjTypes.OPER)
        val opers = arg.getChildren(target)

        return opers.isNotEmpty()
    }
}