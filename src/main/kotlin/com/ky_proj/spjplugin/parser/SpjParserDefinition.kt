package com.ky_proj.spjplugin.parser

import com.intellij.lang.*
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.tree.*
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.lexer.SpjLexerAdapter
import com.ky_proj.spjplugin.psi.*

class SpjParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer {
        return SpjLexerAdapter()
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    override fun getCommentTokens(): TokenSet {
        return COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project): PsiParser {
        return SpjParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return SpjFile(viewProvider)
    }

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    override fun createElement(node: ASTNode): PsiElement {
        return SpjTypes.Factory.createElement(node)
    }

    companion object {
        @JvmStatic val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
        @JvmStatic val COMMENTS = TokenSet.create(SpjTypes.COMMENT)
        @JvmStatic val DOC_COMMENTS = TokenSet.create(SpjTypes.DOC_COMMENT)

        // プロシージャ
        @JvmStatic val PROCEDURE = TokenSet.create(SpjTypes.PROCEDURE)

        // 何もない
        @JvmStatic val DUMMY = TokenSet.create(TokenType.DUMMY_HOLDER)

        @JvmStatic val FILE = IFileElementType(Language.findInstance<SpjLanguage>(SpjLanguage::class.java!!))
    }
}