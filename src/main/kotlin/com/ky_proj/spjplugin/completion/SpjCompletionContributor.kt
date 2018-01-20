package com.ky_proj.spjplugin.completion

/**
 * Created on 2018/01/19.
 */
import com.intellij.codeInsight.AutoPopupController
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilderUtil
import com.intellij.mock.MockFileManager
import com.intellij.mock.MockPsiManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Condition
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.objectTree.ObjectTree
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.impl.PsiElementFactoryImpl
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.intellij.util.messages.MessageBus
import com.ky_proj.spjplugin.completion.inserthandler.CommandInsertHandler
import com.ky_proj.spjplugin.filetype.SpjFileType
import com.ky_proj.spjplugin.icon.SpjIcon
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.psi.*
import com.ky_proj.spjplugin.psi.impl.SpjNamedElementImpl
import com.ky_proj.spjplugin.psi.impl.SpjProcedureDefImpl
import com.ky_proj.spjplugin.util.*
import com.ky_proj.spjplugin.util.SpjUtil
import org.picocontainer.PicoContainer

import java.util.ArrayList
import java.util.Collections

class SpjCompletionContributor : CompletionContributor() {

    init {
        // コマンド
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.COMMAND_CALL).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                        val list = SpjCommandProvider.list(parameters.originalFile.project)
                        list.forEach { command ->
                            val element = LookupElementBuilder.create(command, command.node.text)
                                    .withTypeText("built in command", true)
                                    .withInsertHandler(CommandInsertHandler())
                                    .withIcon(SpjIcon.FILE)
                            resultSet.addElement(element)
                        }
                    }
                }
        )


        // 変数
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.VARIABLE).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                        // 組み込み関数
                        val list = SpjFunctionProvider.list(parameters.originalFile.project)
                        list.forEach { command ->
                            val element = LookupElementBuilder.create(command, command.node.text)
                                    .withTypeText("built in function", true)
                                    .withInsertHandler(CommandInsertHandler())
                                    .withIcon(SpjIcon.FILE)
                            resultSet.addElement(element)
                        }
//                        // 定義済みプロシージャ
//                        if (SpjUtil.GET_SPJ_SETTING.isEnhanceMode(parameters.originalFile.project)) {
//                            addProceduresToResultSet(resultSet, true)
//                        }

                        // 変数と引数
                        val vars = GetVariables(parameters.originalFile)
                        val variables = vars.variables
                        for (variable in variables) {
                            resultSet.addElement(LookupElementBuilder.create(variable))
                            resultSet.addElement(LookupElementBuilder.create("$" + variable))
                        }
                    }
                }
        )

        /*
        // 関数
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.FUNCTION).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext,
                                                       resultSet: CompletionResultSet) {

                        // neoの組み込み関数
                        val commands = SpjUtil.GET_FUNCTIONS.getList()
                        for (i in commands.indices) {
                            resultSet.addElement(LookupElementBuilder.create(commands[i]))
                        }

                        // 定義済みプロシージャ
                        if (SpjUtil.GET_SPJ_SETTING.isEnhanceMode(myProject)) {
                            addProceduresToResultSet(resultSet, true)
                        }

                    }
                }
        )

        // 引数
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.ARGUMENT).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext,
                                                       resultSet: CompletionResultSet) {

                        // 変数と引数
                        val vars = GetVariables(myFile)
                        val variables = vars.getVariables()
                        for (`var` in variables) {
                            resultSet.addElement(LookupElementBuilder.create(`var`))
                            resultSet.addElement(LookupElementBuilder.create("$" + `var`))
                        }


                        // 関数
                        val commands = SpjUtil.GET_FUNCTIONS.getList()
                        for (i in commands.indices) {
                            resultSet.addElement(LookupElementBuilder.create(commands[i]))
                        }

                        // 定義済みプロシージャ
                        if (SpjUtil.GET_SPJ_SETTING.isEnhanceMode(myProject)) {
                            addProceduresToResultSet(resultSet, true)
                        }


                    }
                }
        )

        // プロシージャ呼び出し
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.PROCEDURE_CALL).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext,
                                                       resultSet: CompletionResultSet) {

                        addProceduresToResultSet(resultSet, false)
                    }
                }
        )

        // SpjDoc
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.DOC_COMMENT_TAG).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext,
                                                       resultSet: CompletionResultSet) {

                        val element = parameters.position
                        val proc_block = PsiTreeUtil.getParentOfType(element, SpjProcedureBlock::class.java)
                        var proc_def: ASTNode? = null
                        if (proc_block != null) {
                            proc_def = proc_block.node.findChildByType(SpjTypes.PROCEDURE_DEF)
                        }

                        if (proc_def != null) {
                            // プロシージャブロック内

                            // @example
                            var str_example = "@example perform "
                            val body = proc_def.findChildByType(SpjTypes.PROCEDURE)
                            val arg = proc_def.findChildByType(SpjTypes.ARGUMENTS)

                            if (arg != null && body != null) {
                                str_example = str_example + body.text + arg.text
                            } else if (body != null) {
                                str_example = str_example + body.text + "()"
                            }
                            resultSet.addElement(LookupElementBuilder.create(str_example))

                            // @param
                            if (arg != null) {
                                val ags = PsiTreeUtil.findChildrenOfType(arg.psi, SpjArgs::class.java)
                                for (ag in ags) {
                                    resultSet.addElement(LookupElementBuilder.create("@param " + ag.text + " "))

                                }

                            }

                        } else {
                            // プロシージャブロック外
                        }

                    }
                }
        )
        */

    }

    /*
    /**
     * 定義済みのプロシージャを追加するやつ
     * @param resultSet
     */
    private fun addProceduresToResultSet(resultSet: CompletionResultSet, is_only_with_return: Boolean?) {
        val proc_defs: List<SpjProcedureDef>


        proc_defs = SpjUtil.GET_PROCS.getProcDefs(myProject, is_only_with_return)

        val neoDefault = SpjUtil.GET_PROCS.getNeoSysProcs()

        for (i in proc_defs.indices) {
            val nm: String
            val proc = proc_defs[i]
            val body = proc.node.findChildByType(SpjTypes.PROCEDURE)
            val arg = proc.node.findChildByType(SpjTypes.ARGUMENTS)

            if (arg != null && body != null) {
                nm = body.text + arg.text
            } else if (body != null) {
                nm = body.text + "()"
            } else {
                continue
            }

            if (!neoDefault.contains(body.text)) {
                // システムプロシージャは除外
                resultSet.addElement(
                        LookupElementBuilder.create(nm)
                                .withTypeText(
                                        proc.containingFile.name
                                ).withInsertHandler { insertionContext, lookupElement -> }
                )
            }
        }
    }
    */
}