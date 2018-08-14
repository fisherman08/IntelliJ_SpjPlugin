package com.ky_proj.spjplugin.folding

/**
 * Created on 2018/01/29.
 */
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.*
import com.intellij.openapi.editor.*
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.*

import java.util.*

class  SpjFoldingBuilder : FoldingBuilderEx() {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = ArrayList<FoldingDescriptor>()

        // ifやfor、whileなどblockの折りたたみ
        foldBlocks(descriptors, root)

        // プロシージャブロックごとの折りたたみ
        foldProcedureBlock(descriptors = descriptors, root = root)

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String? {
        return "..."
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return (node.text.length > 1200)
    }


    private fun foldBlocks(descriptors: ArrayList<FoldingDescriptor>, root: PsiElement){
        val blocks = PsiTreeUtil.findChildrenOfAnyType(root, SpjIfblock::class.java, SpjWhileblock::class.java, SpjForblock::class.java)
        for (block in blocks) {
            if (!arrayOf(SpjTypes.IFBLOCK, SpjTypes.WHILEBLOCK, SpjTypes.FORBLOCK).contains(block.node.elementType)) {
                continue
            }

            val keyword = block.node.findChildByType(TokenSet.create(SpjTypes.CONDITION, SpjTypes.ARGUMENTS))?: continue
            var start_offset = keyword.textRange?.endOffset ?:continue
            var end_offset: Int


            // else ifやelseがあればそれごとにたたむ
            val elses = block.node.getChildren(TokenSet.create(SpjTypes.ELSEIFSTATE, SpjTypes.ELSE))
            for(else_state in elses) {
                end_offset = else_state.textRange?.startOffset ?:continue
                addDescriptor(descriptors, start_offset, end_offset - 1, else_state)
                start_offset = else_state.textRange?.endOffset ?:continue
            }

            // blockの終わり
            val last_element = block.node.findChildByType(TokenSet.create(SpjTypes.ENDIF, SpjTypes.ENDFOR, SpjTypes.ENDWHILE))
            end_offset = last_element?.textRange?.startOffset ?:continue
            addDescriptor(descriptors, start_offset, end_offset - 1 , block.node)
        }
    }


    private fun foldProcedureBlock(descriptors: ArrayList<FoldingDescriptor>, root: PsiElement){
        val blocks = PsiTreeUtil.findChildrenOfType(root, SpjProcedureBlock::class.java)
        for (block in blocks) {
            if (block.node.elementType != SpjTypes.PROCEDURE_BLOCK) {
                continue
            }

            val def = PsiTreeUtil.findChildOfType(block, SpjProcedureDef::class.java) ?: continue

            // スタート位置のオフセットは定義時の終わり
            var start_offset = def.textRange?.endOffset ?:continue
            val end_offset = block?.textRange?.endOffset ?:continue

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

            addDescriptor(descriptors, start_offset, end_offset, block.node)
        }
    }


    private fun addDescriptor(descriptors: ArrayList<FoldingDescriptor>, start_offset: Int, end_offset: Int, node: ASTNode) {
        if (end_offset > start_offset) {
            descriptors.add(object : FoldingDescriptor(
                    node,
                    TextRange(start_offset, end_offset),
                    FoldingGroup.newGroup("block")
            ) {
                override fun getPlaceholderText(): String? {
                    return "..."
                }
            })
        }
    }
}
