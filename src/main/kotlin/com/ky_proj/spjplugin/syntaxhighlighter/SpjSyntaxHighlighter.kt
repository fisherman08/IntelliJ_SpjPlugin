package com.ky_proj.spjplugin.syntaxhighlighter

import com.intellij.lang.ASTNode
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.psi.SpjTypes

import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.ky_proj.spjplugin.lexer.SpjLexerAdapter
import java.util.ArrayList

class SpjSyntaxHighlighter : SyntaxHighlighterBase() {

    private val mapOfTypeToKeys = mapOf(
            SpjTypes.COMMENT           to COMMENT_KEYS,
            SpjTypes.DOC_COMMENT       to DOC_COMMENT_KEYS,
            SpjTypes.DOC_COMMENT_TAG   to DOC_COMMENT_TAG_KEYS,
            SpjTypes.DOC_COMMENT_TAG_DEPRECATED   to DOC_COMMENT_TAG_DEPRECATED_KEYS,
            SpjTypes.DOC_COMMENT_VALUE to DOC_COMMENT_VALUE_KEYS,
            SpjTypes.DOC_COMMENT_TEXT  to DOC_COMMENT_TEXT_KEYS,
            SpjTypes.STRING            to STRING_KEYS,
            SpjTypes.SEPARATOR         to SEPARATOR_KEYS,
            SpjTypes.LPAR  to PARENTHESES_KEYS,
            SpjTypes.RPAR  to PARENTHESES_KEYS,
            SpjTypes.LPARC to PARENTHESES_KEYS,
            SpjTypes.RPARC to PARENTHESES_KEYS,
            SpjTypes.COMMA to COMMA_KEYS,
            SpjTypes.PROCEDURE to PROCEDURE_KEYS,
            SpjTypes.LBLO      to PROCEDURE_KEYS,
            SpjTypes.RBLO      to PROCEDURE_KEYS,
            SpjTypes.KEYWORD to KEYWORD_KEYS,
            SpjTypes.RETURN  to KEYWORD_KEYS,
            SpjTypes.PERFORM to KEYWORD_KEYS,
            SpjTypes.CALL    to KEYWORD_KEYS,
            SpjTypes.IF      to KEYWORD_KEYS,
            SpjTypes.ELSE    to KEYWORD_KEYS,
            SpjTypes.ENDIF   to KEYWORD_KEYS,
            SpjTypes.ELSEIF  to KEYWORD_KEYS,
            SpjTypes.THEN    to KEYWORD_KEYS,
            SpjTypes.FOR     to KEYWORD_KEYS,
            SpjTypes.ENDFOR  to KEYWORD_KEYS,
            SpjTypes.WHILE   to KEYWORD_KEYS,
            SpjTypes.ENDWHILE to KEYWORD_KEYS,
            SpjTypes.CATCH   to KEYWORD_KEYS,
            SpjTypes.TRY     to KEYWORD_KEYS,
            SpjTypes.EXCEPT  to KEYWORD_KEYS,
            SpjTypes.FINALLY to KEYWORD_KEYS,
            SpjTypes.ENDTRY  to KEYWORD_KEYS,
            SpjTypes.OPER  to OPER_KEYS,
            SpjTypes.ORAND to OPER_KEYS,
            SpjTypes.PROCEDURE_CALL to PROCEDURE_CALL_KEYS,
            SpjTypes.COMMAND_CALL   to COMMAND_CALL_KEYS,
            SpjTypes.FUNCTION       to COMMAND_CALL_KEYS,
            SpjTypes.VARIABLE to VARIABLE_KEYS,
            SpjTypes.ARGUMENT to ARGUMENT_KEYS,
            SpjTypes.BAD_CHARACTER to BAD_CHAR_KEYS,
            SpjTypes.NUMBER to NUMBER_KEYS
    )


    override fun getHighlightingLexer(): Lexer {
        return SpjLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return mapOfTypeToKeys[tokenType] ?: EMPTY_KEYS
    }


    companion object {
        @JvmStatic val SEPARATOR = createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        @JvmStatic val PROCEDURE = createTextAttributesKey("SPJ_PROCEDURE", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
        @JvmStatic val PROCEDURE_CALL = createTextAttributesKey("SPJ_PROCEDURE_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        @JvmStatic val COMMAND_CALL = createTextAttributesKey("SPJ_COMMAND_CALL", DefaultLanguageHighlighterColors.INSTANCE_METHOD)

        @JvmStatic val KEYWORD = createTextAttributesKey("SPJ_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)

        @JvmStatic val PARENTHESES = createTextAttributesKey("SPJ_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES)
        @JvmStatic val COMMA = createTextAttributesKey("SPJ_COMMA", DefaultLanguageHighlighterColors.COMMA)
        @JvmStatic val STRING = createTextAttributesKey("SPJ_String", DefaultLanguageHighlighterColors.STRING)

        @JvmStatic val OPER = createTextAttributesKey("SPJ_Oper", DefaultLanguageHighlighterColors.OPERATION_SIGN)

        @JvmStatic val VARIABLE = createTextAttributesKey("SPJ_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
        @JvmStatic val ARGUMENT = createTextAttributesKey("SPJ_ARGUMENT", DefaultLanguageHighlighterColors.PARAMETER)
        @JvmStatic val VARIABLE_NAME = createTextAttributesKey("SPJ_VARIABLE_NAME", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE)

        @JvmStatic val NUMBER = createTextAttributesKey("SPJ_NUMBER", DefaultLanguageHighlighterColors.NUMBER)

        @JvmStatic val COMMENT = createTextAttributesKey("SPJ_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        @JvmStatic val DOC_COMMENT = createTextAttributesKey("SPJ_DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT)
        @JvmStatic val DOC_COMMENT_TAG = createTextAttributesKey("SPJ_DOC_COMMENT_TAG", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG)
        @JvmStatic val DOC_COMMENT_TAG_DEPRECATED = createTextAttributesKey("SPJ_DOC_COMMENT_TAG_DEPRECATED", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG)
        @JvmStatic val DOC_COMMENT_VALUE = createTextAttributesKey("SPJ_DOC_COMMENT_VALUE", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG_VALUE)
        @JvmStatic val DOC_COMMENT_TEXT = createTextAttributesKey("SPJ_DOC_COMMENT_TEXT", DefaultLanguageHighlighterColors.DOC_COMMENT_MARKUP)
        @JvmStatic val BAD_CHARACTER = createTextAttributesKey("SPJ_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)


        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)

        private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val DOC_COMMENT_KEYS = arrayOf(DOC_COMMENT)
        private val DOC_COMMENT_TAG_KEYS = arrayOf(DOC_COMMENT_TAG)
        private val DOC_COMMENT_TAG_DEPRECATED_KEYS = arrayOf(DOC_COMMENT_TAG_DEPRECATED)
        private val DOC_COMMENT_VALUE_KEYS = arrayOf(DOC_COMMENT_VALUE)
        private val DOC_COMMENT_TEXT_KEYS = arrayOf(DOC_COMMENT_TEXT)

        private val PROCEDURE_KEYS = arrayOf(PROCEDURE)
        private val PROCEDURE_CALL_KEYS = arrayOf(PROCEDURE_CALL)
        private val COMMAND_CALL_KEYS = arrayOf(COMMAND_CALL)

        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val PARENTHESES_KEYS = arrayOf(PARENTHESES)
        private val COMMA_KEYS = arrayOf(COMMA)
        private val STRING_KEYS = arrayOf(STRING)

        private val OPER_KEYS = arrayOf(OPER)

        private val VARIABLE_KEYS = arrayOf(VARIABLE)
        private val ARGUMENT_KEYS = arrayOf(ARGUMENT)
        private val VARIABLE_NAME_KEYS = arrayOf(VARIABLE_NAME)
        private val NUMBER_KEYS = arrayOf(NUMBER)

        private val EMPTY_KEYS = ArrayList<TextAttributesKey>().toTypedArray()
    }
}