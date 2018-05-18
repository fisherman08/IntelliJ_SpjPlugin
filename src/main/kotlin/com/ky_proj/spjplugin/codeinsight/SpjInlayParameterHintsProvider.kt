package com.ky_proj.spjplugin.codeinsight

import com.intellij.codeInsight.hints.*
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.psi.SpjArguments
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjCommandProvider
import com.ky_proj.spjplugin.util.SpjFunctionProvider

class SpjInlayParameterHintsProvider : InlayParameterHintsProvider {
    companion object {
        fun getInstance() = InlayParameterHintsExtension.forLanguage(SpjLanguage.INSTANCE) as SpjInlayParameterHintsProvider
    }

    override fun getHintInfo(element: PsiElement): HintInfo.MethodInfo? {
        return null
    }

    override fun getParameterHints(element: PsiElement): List<InlayInfo> {
        return if (TokenSet.create(SpjTypes.ARGUMENTS).contains(element.node.elementType)) {
            getHintsOfArguments(element)
        } else {
            emptyList()
        }
    }

    override fun getDefaultBlackList(): Set<String> {
        return setOf("")
    }


    private fun getHintsOfArguments(arguments: PsiElement): List<InlayInfo> {
        val result = ArrayList<InlayInfo>()
        for (arg in getElementsForArgAsNode(arguments)) {
            result.addAll(getHintsOfArg(arg, arguments))
        }
        return result
    }


    private fun getHintsOfArg(node: ASTNode, arguments: PsiElement): List<InlayInfo> {
        // 呼び出し側のelement
        if (arguments.node.elementType != SpjTypes.ARGUMENTS) {
            // argumentsじゃなかったら何もしない
            return emptyList()
        }

        val caller = arguments.parent ?: return emptyList()
        val callerNode = caller.node ?: return emptyList()

        if (!TokenSet.create(SpjTypes.CALLING_PROCEDURE, SpjTypes.CALLING_FUNCTION, SpjTypes.CALLING_COMMAND).contains(callerNode.elementType)) {
            // hintsを出すのはプロシージャ、関数、コマンドの呼び出し時のみ
            return emptyList()
        }

        // 定義元を探す。特定できなければ無視。
        val def = getDefinition(caller) ?: return emptyList()
        if (def === caller) {
            return emptyList()
        }

        val definedArguments = PsiTreeUtil.findChildOfType(def, SpjArguments::class.java) ?: return emptyList()

        // 呼び出し側の引数の順番
        val callerIndex = getElementsForArgAsNode(arguments).indexOf(node)

        // 定義元の引数リスト
        val definedArgumentsList = getElementsForArgAsNode(definedArguments)
        if (callerIndex == -1 || definedArgumentsList.size <= callerIndex) {
            return emptyList()
        }

        if (definedArgumentsList[callerIndex] != null) {
            return listOf(InlayInfo(definedArgumentsList[callerIndex].text, node.startOffset))
        }

        return emptyList()
    }

    private fun getTokenSetForArg(): TokenSet {
        return TokenSet.create(SpjTypes.ARGS, SpjTypes.NUMBER, SpjTypes.CALLING_FUNCTION, SpjTypes.STRING)
    }


    private fun getElementsForArgAsNode(arguments: PsiElement): Array<ASTNode> {
        val result = ArrayList<ASTNode>()

        for (child in arguments.node.getChildren(getTokenSetForArg())) {
            if (getTokenSetForArg().contains(child.elementType)) {
                result.add(child)
            }
        }
        return result.toTypedArray()
    }

    private fun getDefinition(element: PsiElement): PsiElement? {
        return when (element.node.elementType) {
            SpjTypes.CALLING_PROCEDURE -> {
                element.reference?.resolve()
            }
            SpjTypes.CALLING_FUNCTION -> {
                val def = element.reference?.resolve()
                if (def !== element) {
                    // 定義されたプロシージャ
                    def
                } else {
                    // 組み込み関数
                    val elemName = element.node.findChildByType(SpjTypes.FUNCTION)?.text ?: return null
                    SpjFunctionProvider.getDefinitionByName(element.project, elemName)
                }
            }
            SpjTypes.CALLING_COMMAND -> {
                val elemName = element.node.findChildByType(SpjTypes.COM_CALL)?.text ?: return null
                SpjCommandProvider.getDefinitionByName(element.project, elemName)
            }
            else -> {
                null
            }
        }
    }
}