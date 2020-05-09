package com.ky_proj.spjplugin.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.util.IncorrectOperationException
import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.psi.SpjPsiUtil
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.reference.SpjReference

open class SpjNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), PsiNamedElement {
    private var myRef: SpjReference? = null

    override fun getReference(): SpjReference? {
        val ref = myRef ?:SpjReference(this)
        // getReferenceが呼ばれるたびにSpjReferenceをインスタンス化するとパフォーマンスが大変なことになるのでキャッシュしておく
        myRef = ref

        return ref
    }

    override fun getName(): String? {
        var result :String = ""

        if (isTypeOf(SpjTypes.PROCEDURE_DEF)) {
            val proc = this.node.findChildByType(SpjTypes.PROCEDURE)
            result = proc?.text ?: ""
        }

        return result
    }

    @Throws(IncorrectOperationException::class)
    override fun setName(name: String): PsiElement {
        if(isTypeOf(SpjTypes.PROCEDURE_DEF) && name.isNotEmpty()){
            val old_node = this.node.findChildByType(SpjTypes.PROCEDURE) ?:return this
            val new_node = SpjPsiUtil.createSpjElement(project = this.project, content = "[${name}]", type = SpjTypes.PROCEDURE).node

            this.node.replaceChild(old_node, new_node)
        }

        return this
    }

    override fun isValid(): Boolean {
        return super.isValid()
    }

    fun isTypeOf(type : IElementType): Boolean {
        return (this.node.elementType === type)
    }
}
