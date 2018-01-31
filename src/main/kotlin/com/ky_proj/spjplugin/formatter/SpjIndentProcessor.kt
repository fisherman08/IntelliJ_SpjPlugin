package com.ky_proj.spjplugin.formatter

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.psi.SpjTypes.*

class SpjIndentProcessor {

    fun getChildIndent(node: ASTNode): Indent {

        if (node.elementType === TokenType.ERROR_ELEMENT) {
            return Indent.getNormalIndent()
        }
        if (node.textRange.length == 0) {
            return Indent.getNormalIndent()
        }

        val elementType = node.elementType
        val parent = node.treeParent
        val parentType = parent?.elementType

        if (parent == null || parent.treeParent == null) {
            return Indent.getNoneIndent()
        }

        return when(parentType){
            IFBLOCK -> {
                getIndentIfBlock(elementType)
            }
            FORBLOCK -> {
                getIndentForBlock(elementType)
            }
            WHILEBLOCK -> {
                getIndentWhileBlock(elementType)
            }
            TRYBLOCK -> {
                getIndentTryBlock(elementType)
            }
            else ->{
                Indent.getNoneIndent()
            }
        }

    }

    private fun getIndentIfBlock(elementType :IElementType) :Indent {
        return if (
                TokenSet.create(
                        SpjTypes.IF,
                        SpjTypes.ELSE,
                        SpjTypes.ELSEIF,
                        SpjTypes.ELSEIFSTATE,
                        SpjTypes.THEN,
                        SpjTypes.ENDIF,
                        SpjTypes.CONDITION,
                        SpjTypes.CRLF
                ).contains(elementType)
        ) {
            Indent.getNoneIndent()
        } else {
            Indent.getNormalIndent()
        }
    }

    private fun getIndentForBlock(elementType: IElementType): Indent {
        return if (
                TokenSet.create(
                        SpjTypes.FOR,
                        SpjTypes.ENDFOR,
                        SpjTypes.CRLF
                ).contains(elementType)) {
            Indent.getNoneIndent()
        } else {
            Indent.getNormalIndent()
        }
    }

    private fun getIndentWhileBlock(elementType: IElementType): Indent {
        return if (
                TokenSet.create(
                        SpjTypes.WHILE,
                        SpjTypes.ENDWHILE,
                        SpjTypes.CRLF
                ).contains(elementType)
        ) {
            Indent.getNoneIndent()
        } else {
            Indent.getNormalIndent()
        }
    }

    private fun getIndentTryBlock(elementType: IElementType): Indent {
        return if (TokenSet.create(SpjTypes.TRY, SpjTypes.EXCEPTBLOCK, SpjTypes.EXCEPT, SpjTypes.FINALLY, SpjTypes.ENDTRY).contains(elementType)) {
            Indent.getNoneIndent()
        } else {
            Indent.getNormalIndent()
        }
    }
}
