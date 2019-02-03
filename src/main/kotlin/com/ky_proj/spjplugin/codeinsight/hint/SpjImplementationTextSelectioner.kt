package com.ky_proj.spjplugin.codeinsight.hint

import com.intellij.codeInsight.hint.ImplementationTextSelectioner
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.SpjProcedureBlock
import com.ky_proj.spjplugin.psi.SpjProcedureDef
import com.ky_proj.spjplugin.psi.SpjTypes

/**
 * QuickDefinitionで定義元を開いたときにポップアップで表示される範囲を定義するクラス
 */
class SpjImplementationTextSelectioner: ImplementationTextSelectioner {

    override fun getTextStartOffset(parent: PsiElement): Int {
        val textRange = parent.textRange
        return textRange!!.startOffset
    }

    override fun getTextEndOffset(element: PsiElement): Int {

        // typeによって返す
        return when(element.node.elementType){
            SpjTypes.PROCEDURE_DEF -> {
                // procedure定義なら親のブロックのendOffsetを返す
                getEnndOffsetOfProcedureDef(def = element as SpjProcedureDef)
            } else -> {
                // 通常は自分自身だけを返す
                element.textRange!!.endOffset
            }
        }

    }

    /**
     * プロシージャ定義から親のブロックを探してそのendoffsetを返却する
     */
    private fun getEnndOffsetOfProcedureDef(def: SpjProcedureDef) : Int{
        val block = PsiTreeUtil.getParentOfType(def, SpjProcedureBlock::class.java)

        return if(block != null){
            block.textRange!!.endOffset
        } else {
            def.textRange!!.endOffset
        }
    }
}
