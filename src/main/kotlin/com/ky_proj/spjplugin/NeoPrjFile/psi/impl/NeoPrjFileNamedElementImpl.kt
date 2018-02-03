package com.ky_proj.spjplugin.NeoPrjFile.psi.impl

/**
 * Created on 2018/02/03.
 */
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.util.IncorrectOperationException

open class NeoPrjFileNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {

    /*public SpjReference[] getReferences(){
        SpjReference[] result = new SpjReference[1];
        SpjReference ref = new SpjReference(this);
        result[0] = ref;
        return result;
    }*/

    val nameIdentifier: PsiElement?
        get() = null

    override fun getName(): String? {

        return ""
    }

    @Throws(IncorrectOperationException::class)
    override fun setName(name: String): PsiElement {
        return this
    }

    override fun isValid(): Boolean {
        return super.isValid()
    }
}