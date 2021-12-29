package com.ky_proj.spjplugin.annotator

/**
 * Created on 2018/01/27.
 */
import com.intellij.lang.ASTNode
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.language.SpjNamesValidator
import com.ky_proj.spjplugin.psi.*
import com.ky_proj.spjplugin.setting.SpjSetting
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import com.ky_proj.spjplugin.util.SpjTreeUtil
import kotlinx.coroutines.runBlocking

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
        val procedureName = element.text ?: return
        val project = element.project
        val defs = runBlocking {
            SpjProcedureProvider.findDefinitionInProject(
                project = project,
                name = procedureName,
                is_only_return = false
            )
        }
        if (defs.size == 0) {
            // プロシージャが定義されていない
            holder.newAnnotation(HighlightSeverity.ERROR, "Undefined procedure").range(element).create()
        } else if (defs.size == 1) {
            val def = defs[0]


            val caller = PsiTreeUtil.getParentOfType(element, SpjCallingProcedure::class.java)
            if (caller != null) {
                // 呼び出し元の引数の個数
                annotateArgumentsCount(element, holder, def, caller)
                // @deprecatedなプロシージャを呼んでいる
                annotateDeprecatedProcedure(definition = def, holder = holder, caller = element)
            }

        }
    }

    /**
     * 定義済みプロシージャ呼び出しの時に引数の個数を数える
     *
     * */
    private fun annotateArgumentsCount(
        element: PsiElement,
        holder: AnnotationHolder,
        def: SpjProcedureDef,
        caller: PsiElement
    ) {
        val neoVersion = SpjSetting(element.project).getNeoVersion()

        val argCountDefined = getArgsCount(def)
        val argCountCalled = getArgsCount(caller)

        // performループのときはOK
        // 定義元のargumentが０個で、呼び出し元のargumentの中にOPERが1つ以上ある
        if (argCountDefined == 0 && isOperandContained(caller)) {
            if (neoVersion >= 3.0) {
                holder.newAnnotation(
                    HighlightSeverity.WARNING,
                    "Perform loops are deprecated. Use 'for' or 'while' instead"
                ).range(caller).create()
            }
            return
        }


        if (argCountCalled > argCountDefined) {

            // 呼び出し時の個数が定義より多かったらneo4でエラー、それ以前ならワーニング
            if (neoVersion < 4.0) {
                holder.newAnnotation(HighlightSeverity.WARNING, "Number of arguments is bigger than definition").range(element).create()
            } else {
                holder.newAnnotation(HighlightSeverity.ERROR, "Too many arguments").range(element).create()
            }


        } else if (argCountCalled < argCountDefined) {

            // 呼び出し時の個数が定義より少なかったwarning
            holder.newAnnotation(HighlightSeverity.WARNING, "Number of arguments is smaller than definition").range(element).create()
        }
    }

    // プロシージャ定義
    private fun annotateProcedureName(element: PsiElement, holder: AnnotationHolder) {

        // NEO4以上でセミコロンが入っていたらアウト
        utilAnnotateInvalidCharacter(element, holder)

        // 定義なのに引数に関数が入っていたらアウトにする
        val invalidChildren = PsiTreeUtil.findChildrenOfType(element, SpjCallingFunction::class.java)
        for (child in invalidChildren) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid argument inside procedure definitions").range(child).create()
        }
    }

    // プロシージャ非推奨
    private fun annotateDeprecatedProcedure(definition: PsiElement, holder: AnnotationHolder, caller: PsiElement) {

        val block = PsiTreeUtil.getParentOfType(definition, SpjProcedureBlock::class.java)
        if (block != null && SpjTreeUtil.findChildByTokenType(
                block,
                TokenSet.create(SpjTypes.DOC_COMMENT_TAG_DEPRECATED)
            ).isNotEmpty()
        ) {
            holder.newAnnotation(HighlightSeverity.WARNING, "This procedure is deprecated").range(caller).create()
        }
    }

    // 関数呼び出し
    private fun annotateCallingFunction(element: PsiElement, holder: AnnotationHolder) {
        val procedureName = element.text ?: return

        val project = element.project
        val isEnhanceMode = SpjSetting(project).isEnhanceMode()

        // 不正な文字を含んでいる
        utilAnnotateInvalidCharacter(element, holder)

        val defs = runBlocking {
            SpjProcedureProvider.findDefinitionInProject(
                project = project,
                name = procedureName,
                is_only_return = false
            )
        }


        if (!isEnhanceMode) {
            // エンハンスモードじゃいのにユーザー定義プロシージャを関数形式で呼んでいる
            if (defs.size > 0) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Enable EnhanceMode to get values via 'return'").range(element).create()
            }
        }

        if (isEnhanceMode && defs.size == 1) {
            val def = defs.first()

            // returnを含んでいないのに関数形式で呼び出している
            val block = PsiTreeUtil.getParentOfType(def, SpjProcedureBlock::class.java)
            if (block != null && SpjTreeUtil.findChildByTokenType(block, TokenSet.create(SpjTypes.RETURN)).isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "This procedure does not include 'return'").range(element).create()
            }

            // 引数の個数
            val caller = PsiTreeUtil.getParentOfType(element, SpjCallingFunction::class.java)
            if (caller != null) {
                annotateArgumentsCount(element, holder, def, element)
                // @deprecatedなプロシージャを呼んでいる
                annotateDeprecatedProcedure(definition = def, holder = holder, caller = element)
            }

        }


    }

    // 変数
    private fun annotateVariable(element: PsiElement, holder: AnnotationHolder) {
        val variableName = element.text ?: return

        if (variableName.isNotEmpty() && variableName.substring(0, 1) == "＄") {
            holder.newAnnotation(HighlightSeverity.WARNING, "May be '$'").range(element).create()
        }
        // NEO4以上でセミコロンが入っていたらアウト
        utilAnnotateInvalidCharacter(element, holder)

    }

    // 引数ブロック
    private fun annotateArguments(element: PsiElement, holder: AnnotationHolder) {
        val neoVersion = SpjSetting(element.project).getNeoVersion()

        // neo3以前なら問題ない
        if (neoVersion < 4.0) {
            return
        }

        // computeの時だけは例外として認める
        val command = element.prevSibling
        if (command != null && command.text == "compute") {
            return
        }

        val children = element.node.getChildren(
            TokenSet.create(
                SpjTypes.ARGS,
                SpjTypes.NUMBER,
                SpjTypes.CALLING_FUNCTION,
                SpjTypes.COMMA,
                SpjTypes.STRING,
                SpjTypes.ARGUMENTS,
                SpjTypes.RPAR,
                SpjTypes.OPER
            )
        )
        var previousChild: ASTNode? = null

        // いきなりカンマが来てたらアウト
        if (children.isNotEmpty() && children.first().elementType === SpjTypes.COMMA) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Argument cannot be null").range(element).create()
            return
        }

        for (child in children) {
            // カンマの後にカンマが続くか、arguments自体が終わってたらアウト
            if (previousChild != null &&
                previousChild.elementType === SpjTypes.COMMA &&
                (child.elementType === SpjTypes.COMMA || child.elementType === SpjTypes.RPAR)
            ) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Argument cannot be null").range(element).create()
                break
            }

            // カンマなしで変数が2つ続いてたらアウト
            if (previousChild != null && isValidElementTypeForArgument(previousChild) && isValidElementTypeForArgument(child)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "',' is required to separate arguments").range(child.psi).create()
                break
            }
            previousChild = child
        }

    }

    private fun annotateTryBlock(element: PsiElement, holder: AnnotationHolder) {
        if (SpjSetting(element.project).getNeoVersion() < 4.0) holder.newAnnotation(HighlightSeverity.ERROR, "Try statement is not supported in this version.").range(element).create()
    }



    private fun utilAnnotateInvalidCharacter(element: PsiElement, holder: AnnotationHolder) {
        val setting = SpjSetting(element.project)

        val name = element.text
        if (setting.getNeoVersion() < 4.0 || name.length < 2) return

        // NEO4以上でセミコロンが入っていたらアウト
        val invalidCharacters = SpjNamesValidator.invalid_characters
        for (character in invalidCharacters) {
            if (name.contains(character)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Invalid Character '$character' included").range(element).create()
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
        val arguments = arg.getChildren(
            TokenSet.create(
                SpjTypes.ARGS,
                SpjTypes.NUMBER,
                SpjTypes.CALLING_FUNCTION,
                SpjTypes.STRING,
                SpjTypes.ARGUMENTS
            )
        )

        return arguments.size
    }

    private fun isOperandContained(element: PsiElement): Boolean {
        val arg = element.node.findChildByType(SpjTypes.ARGUMENTS)?: return false
        return arg.getChildren(TokenSet.create(SpjTypes.OPER)).isNotEmpty()
    }
}
