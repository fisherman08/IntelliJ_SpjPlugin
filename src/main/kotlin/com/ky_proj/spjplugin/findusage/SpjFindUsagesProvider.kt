package com.ky_proj.spjplugin.findusage

/**
 * Created on 2018/01/30.
 */
import com.intellij.lang.cacheBuilder.*
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.*
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.*
import com.ky_proj.spjplugin.lexer.SpjLexerAdapter

class SpjFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner? {
        return DefaultWordsScanner(SpjLexerAdapter(),
                TokenSet.create(SpjTypes.PROCEDURE),
                TokenSet.create(SpjTypes.COMMENT),
                TokenSet.EMPTY)
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        val elementType = psiElement.node.elementType
        return (elementType === SpjTypes.PROCEDURE_DEF || elementType === SpjTypes.CALLING_PROCEDURE)

    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }

    override fun getType(element: PsiElement): String {
        return if (element.node.elementType === SpjTypes.PROCEDURE_DEF) {
            "Procedure"
        } else {
            ""
        }
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return if (element.node.elementType === SpjTypes.PROCEDURE_DEF) {
            ""
        } else {
            ""
        }
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return if (element.node.elementType === SpjTypes.PROCEDURE_DEF) {
            element.text
        } else {
            ""
        }
    }
}