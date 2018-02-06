package com.ky_proj.spjplugin.codeinsight

import com.intellij.codeInsight.hints.*
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.psi.SpjTypes

class SpjInlayParameterHintsProvider : InlayParameterHintsProvider {
    companion object {
        fun getInstance() = InlayParameterHintsExtension.forLanguage(SpjLanguage.INSTANCE) as SpjInlayParameterHintsProvider
    }

    override fun getHintInfo(element: PsiElement): HintInfo.MethodInfo? {
        /*if (element is PsiCallExpression) {
            val resolvedElement = element.resolveMethodGenerics().element
            if (resolvedElement is PsiMethod) {
                return getMethodInfo(resolvedElement)
            }
        }*/
        return null
    }
    override fun getParameterHints(element: PsiElement): List<InlayInfo> {
        return if(getTokenSetForArg().contains(element.node.elementType)) {
            getHintsOfArg(element)
        } else{
            emptyList()
        }
    }
    override fun getDefaultBlackList() :Set<String> {
        return setOf("")
    }

    private fun getHintsOfArg(element: PsiElement) :List<InlayInfo> {
        val arguments = element.parent   ?: return emptyList()
        val caller    = arguments.parent ?: return emptyList()

        if(arguments.node.elementType != SpjTypes.ARGUMENTS)
            return emptyList()

        if(caller.node.elementType != SpjTypes.CALLING_PROCEDURE && caller.node.elementType != SpjTypes.CALLING_FUNCTION)
            return emptyList()

        val def = caller.reference?.resolve() ?: return emptyList()
        val definedArguments = def.node.findChildByType(SpjTypes.ARGUMENTS)?.getChildren(TokenSet.create(SpjTypes.ARGS)) ?: return emptyList()

        if(definedArguments.isEmpty()) return emptyList()

        return listOf(InlayInfo("arg", element.node.startOffset))
    }

    private fun getTokenSetForArg() :TokenSet {
        return TokenSet.create(SpjTypes.ARGS, SpjTypes.NUMBER, SpjTypes.CALLING_FUNCTION, SpjTypes.STRING)
    }
}