package com.ky_proj.spjplugin.completion

/**
 * Created on 2018/01/19.
 */
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.ASTNode
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.ky_proj.spjplugin.completion.inserthandler.CommandInsertHandler
import com.ky_proj.spjplugin.completion.inserthandler.ProcedureInsertHandler
import com.ky_proj.spjplugin.icon.SpjIcon
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.psi.*
import com.ky_proj.spjplugin.setting.SpjSetting
import com.ky_proj.spjplugin.util.*

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
        addFunctions(SpjTypes.VARIABLE)
        addVariablesAndArguments(SpjTypes.VARIABLE)
        addProcedures(SpjTypes.VARIABLE, true)

        // 関数
        addFunctions(SpjTypes.FUNCTION)
        addVariablesAndArguments(SpjTypes.FUNCTION)
        addProcedures(SpjTypes.FUNCTION, true)

        // 引数
        addFunctions(SpjTypes.ARGUMENT)
        addVariablesAndArguments(SpjTypes.ARGUMENT)
        addProcedures(SpjTypes.ARGUMENT, true)

        // プロシージャ呼び出し
        addProcedures(SpjTypes.PROCEDURE_CALL, false)

        // SpjDoc
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(SpjTypes.DOC_COMMENT_TAG).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext,
                                                       resultSet: CompletionResultSet) {
                        val element = parameters.position
                        val procedureDefinition = PsiTreeUtil.getParentOfType(element, SpjProcedureBlock::class.java)?.node?.findChildByType(SpjTypes.PROCEDURE_DEF)
                                ?: return

                        // @example
                        val procedureName = procedureDefinition.findChildByType(SpjTypes.PROCEDURE)?.text ?: return
                        val arguments :ASTNode? = procedureDefinition.findChildByType(SpjTypes.ARGUMENTS)
                        val argumentsText = arguments?.text ?: "()"

                        resultSet.addElement(LookupElementBuilder.create("@example perform " + procedureName + argumentsText))

                        // @param
                        if (arguments != null) {
                            val ags = PsiTreeUtil.findChildrenOfType(arguments.psi, SpjArgs::class.java)
                            for (ag in ags) {
                                if(! ag.text.isEmpty()){
                                    resultSet.addElement(LookupElementBuilder.create("@param " + ag.text + " "))
                                }

                            }

                        }


                    }
                }
        )


    }

    /**
        指定されたタイプのcompletionにNeoの組み込み関数と、関数形式で呼び出せる定義済みプロシージャを加える
     */
    private fun addFunctions(elementtype :IElementType){
        // neoの組み込み関数
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(elementtype).withLanguage(SpjLanguage.INSTANCE),
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
                    }
                }
        )

    }

    private fun addVariablesAndArguments(type :IElementType){
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(type).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
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

    }

    private fun addProcedures(type: IElementType, onlyWithReturn: Boolean) {

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(type).withLanguage(SpjLanguage.INSTANCE),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        val setting = SpjSetting(project)

                        if (!setting.isEnhanceMode() && type != SpjTypes.PROCEDURE_CALL )
                            // EnhanceModeじゃなければ関数形式の呼び出しには対応させない
                            return

                        val procedures = SpjProcedureProvider.listInProject(project, onlyWithReturn)

                        for (procedure in procedures) {
                            val body = procedure.node.findChildByType(SpjTypes.PROCEDURE)?.text ?: continue
                            val arg = procedure.node.findChildByType(SpjTypes.ARGUMENTS)?.text ?: "()"
                            val lookUpString = body + arg

                            val element = LookupElementBuilder.create(procedure, lookUpString)
                                    .withTypeText(procedure.containingFile.name, true)
                                    .withInsertHandler(ProcedureInsertHandler())
                                    .withIcon(SpjIcon.FILE)

                            resultSet.addElement(element)

                        }

                    }
                }
        )


    }

}