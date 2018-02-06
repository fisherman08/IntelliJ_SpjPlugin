package com.ky_proj.spjplugin.codeinsight

import com.intellij.codeInsight.hints.*
import com.intellij.psi.PsiCallExpression
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.psi.SpjTypes

class SpjInlayParameterHintsProvider : InlayParameterHintsProvider {
    companion object {
        fun getInstance() = InlayParameterHintsExtension.forLanguage(SpjLanguage.INSTANCE) as SpjInlayParameterHintsProvider
    }

    override fun getHintInfo(element: PsiElement): HintInfo.MethodInfo? {
//        if (element is PsiCallExpression) {
//            val resolvedElement = element.resolveMethodGenerics().element
//            if (resolvedElement is PsiMethod) {
//                return getMethodInfo(resolvedElement)
//            }
//        }
        return null
    }
    override fun getParameterHints(element: PsiElement): List<InlayInfo> {
//        if (element is PsiCallExpression) {
//            return JavaInlayHintsProvider.createHints(element).toList()
//        }

        return if(element.node.elementType == SpjTypes.ARGS) {
            listOf(InlayInfo("parameter", element.node.startOffset))
        } else{
            emptyList<InlayInfo>()
        }
    }
    override fun getDefaultBlackList() :Set<String> {
        return setOf("")
    }

}