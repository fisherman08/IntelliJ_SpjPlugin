package com.ky_proj.spjplugin.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes

import com.ky_proj.spjplugin.psi.SpjTypes.*

import java.util.ArrayList

class SpjBlock constructor(node: ASTNode, wrap: Wrap?, alignment: Alignment?, private val spacingBuilder: SpacingBuilder) : AbstractBlock(node, wrap, alignment) {

    private val myIndent: Indent = SpjIndentProcessor().getChildIndent(node)

    private val firstChildAlignment: Alignment?
        get() {
            val subBlocks = subBlocks
            for (subBlock in subBlocks) {
                val alignment = subBlock.alignment
                if (alignment != null) {
                    return null
                }
            }
            return null
        }

    override fun buildChildren(): List<Block> {
        val blocks = ArrayList<Block>()
        if (isLeaf) {
            return blocks
        }

        if (myNode.elementType === TokenType.ERROR_ELEMENT || myNode.elementType === TokenType.WHITE_SPACE) {
            return blocks
        }

        if (myNode.textRange.length == 0) {
            return blocks
        }

        var child: ASTNode? = myNode.firstChildNode
        var previousChild: ASTNode? = null
        while (child != null) {
            if (child.elementType !== TokenType.WHITE_SPACE && child.elementType !== TokenType.ERROR_ELEMENT && child.textRange.length != 0 &&
                    (previousChild == null || previousChild.elementType !== SpjTypes.CRLF || child.elementType !== SpjTypes.CRLF)) {
                val block = SpjBlock(child, Wrap.createWrap(WrapType.NORMAL, false), Alignment.createAlignment(), spacingBuilder)
                blocks.add(block)
            }
            previousChild = child
            child = child.treeNext
        }
        return blocks
    }

    override fun getIndent(): Indent? {
        return myIndent
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return ChildAttributes(childIndent, firstChildAlignment)
    }

    override fun getChildIndent(): Indent? {

        return if (TokenSet.create(IFBLOCK, FORBLOCK, WHILEBLOCK, TRYBLOCK).contains(myNode.elementType)) {
            Indent.getNormalIndent()
        } else Indent.getNoneIndent()

    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }
}