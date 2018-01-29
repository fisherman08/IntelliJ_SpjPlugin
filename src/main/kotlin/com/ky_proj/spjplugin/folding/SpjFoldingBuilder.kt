package com.ky_proj.spjplugin.folding

/**
 * Created on 2018/01/29.
 */
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.SpjProcedureBlock
import com.ky_proj.spjplugin.psi.SpjProcedureDef
import com.ky_proj.spjplugin.psi.SpjTypes

import java.util.*

class SpjFoldingBuilder : FoldingBuilderEx() {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = ArrayList<FoldingDescriptor>()


        // プロシージャブロックごとの折りたたみ
        val blocks = PsiTreeUtil.findChildrenOfType(root, SpjProcedureBlock::class.java)
        for (block in blocks) {
            if (block.node.elementType != SpjTypes.PROCEDURE_BLOCK) {
                continue
            }

            val def = PsiTreeUtil.findChildOfType(block, SpjProcedureDef::class.java) ?: continue

            // スタート位置のオフセットは定義時の終わり
            var start_offset = def.textRange?.endOffset ?:continue
            val end_offset = block?.textRange?.endOffset ?:continue
            val group = FoldingGroup.newGroup("block")

            // 先頭にSPj Docが着てたら,foldingのスタート位置はSpjDocの終わりにする
            val children = block.children
            for (child in children) {
                if (child != null && child.node.elementType === SpjTypes.PROCEDURE_DEF) {
                    continue
                } else if (child != null && child.node.elementType === SpjTypes.DOC_COMMENT_ROW) {
                    // spjdoc ならstart offsetを上書き
                    start_offset = child.textRange.endOffset
                } else {
                    // それ以外なら終わり
                    break
                }
            }

            if (end_offset > start_offset) {
                descriptors.add(object : FoldingDescriptor(
                        block.node,
                        TextRange(start_offset, end_offset),
                        group
                ) {
                    override fun getPlaceholderText(): String? {
                        return "..."
                    }
                })
            }


        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String? {
        return "..."
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return (node.text.length > 1200)
    }
}
