package com.ky_proj.spjplugin.codeinsight

import com.intellij.codeInsight.editorActions.QuoteHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import com.ky_proj.spjplugin.psi.SpjTypes


class SpjQuoteHandler : QuoteHandler {
    override fun isClosingQuote(iterator: HighlighterIterator, offset: Int): Boolean {

        if (!isSpjString(iterator)) {
            return false
        }

        val start = iterator.start
        val end = iterator.end
        return (end - start >= 1 && offset == end - 1)

    }

    override fun isOpeningQuote(iterator: HighlighterIterator, offset: Int): Boolean {

        if (!isSpjString(iterator)) {
            return false
        }

        return offset == iterator.start
    }

    override fun hasNonClosedLiteral(editor: Editor, iterator: HighlighterIterator, offset: Int): Boolean {
        try {
            val doc = editor.document
            val chars = doc.charsSequence
            val lineEnd = doc.getLineEndOffset(doc.getLineNumber(offset))

            while (!iterator.atEnd() && iterator.start < lineEnd) {
                if (isSpjString(iterator)) {

                    //if (iterator.start >= iterator.end - 1 || chars[iterator.end - 1] != '\"') {
                    if (
                            iterator.start >= iterator.end - 1 ||
                            (
                                    chars.length >= iterator.start && chars[iterator.start + 1] != '\"')
                    ) {
                        return true
                    }
                }
                iterator.advance()
            }
        } finally {
            while (!iterator.atEnd() && iterator.start != offset) iterator.retreat()
        }

        return false
    }

    override fun isInsideLiteral(iterator: HighlighterIterator): Boolean {
        return isSpjString(iterator)
    }


    private fun isSpjString(iterator: HighlighterIterator): Boolean {
        val chars = iterator.document.charsSequence

        if(chars.length > iterator.start && chars[iterator.start] == '\"'){
            return true
        }

        return iterator.tokenType == SpjTypes.STRING
    }

}
