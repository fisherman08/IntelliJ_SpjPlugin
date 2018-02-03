package com.ky_proj.spjplugin.NeoPrjFile.highlight

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.NeoPrjFile.lexer.NeoPrjFileLexerAdapter
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileTypes
import java.util.ArrayList

/**
 * Created on 2018/02/03.
 */


class NeoPrjFileSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return NeoPrjFileLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when(tokenType) {
            NeoPrjFileTypes.SEPARATOR -> {
                SEPARATOR_KEYS
            }
            NeoPrjFileTypes.KEY -> {
                KEY_KEYS
            }
            NeoPrjFileTypes.VALUE -> {
                VALUE_KEYS
            }
            NeoPrjFileTypes.COMMENT -> {
                COMMENT_KEYS
            }
            TokenType.BAD_CHARACTER -> {
                BAD_CHAR_KEYS
            }
            else -> {
                EMPTY_KEYS
            }
        }
    }

    companion object {
        val SEPARATOR = createTextAttributesKey("NEOPRJFILE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val KEY = createTextAttributesKey("NEOPRJFILE_KEY", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val VALUE = createTextAttributesKey("NEOPRJFILE_VALUE", DefaultLanguageHighlighterColors.STRING)
        val COMMENT = createTextAttributesKey("NEOPRJFILE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = createTextAttributesKey("NEOPRJFILE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val BAD_CHAR_KEYS = arrayOf<TextAttributesKey>(BAD_CHARACTER)
        private val SEPARATOR_KEYS = arrayOf<TextAttributesKey>(SEPARATOR)
        private val KEY_KEYS = arrayOf<TextAttributesKey>(KEY)
        private val VALUE_KEYS = arrayOf<TextAttributesKey>(VALUE)
        private val COMMENT_KEYS = arrayOf<TextAttributesKey>(COMMENT)
        private val EMPTY_KEYS = ArrayList<TextAttributesKey>().toTypedArray()
    }
}