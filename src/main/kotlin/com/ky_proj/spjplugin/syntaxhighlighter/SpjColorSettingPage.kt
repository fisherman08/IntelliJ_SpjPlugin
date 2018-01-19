package com.ky_proj.spjplugin.syntaxhighlighter

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.ky_proj.spjplugin.util.SpjUtil
import com.ky_proj.spjplugin.icon.SpjIcon

import javax.swing.*
import java.io.*

class SpjColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon? {
        return SpjIcon.FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return SpjSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        try {
            return SpjUtil.GET_SAMPLE_SPJ_FILE.sample
        } catch (e: Exception) {
            return ""
        }

    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "SPJ"
    }

    companion object {
        private val DESCRIPTORS = arrayOf(
                AttributesDescriptor("Procedure", SpjSyntaxHighlighter.PROCEDURE),
                AttributesDescriptor("Calling command and function", SpjSyntaxHighlighter.COMMAND_CALL),
                AttributesDescriptor("Calling procedure", SpjSyntaxHighlighter.PROCEDURE_CALL),
                AttributesDescriptor("Keyword", SpjSyntaxHighlighter.KEYWORD),
                AttributesDescriptor("Comment", SpjSyntaxHighlighter.COMMENT),
                AttributesDescriptor("SpjDoc Comment", SpjSyntaxHighlighter.DOC_COMMENT),
                AttributesDescriptor("SpjDoc Tag", SpjSyntaxHighlighter.DOC_COMMENT_TAG),
                AttributesDescriptor("SpjDoc Value", SpjSyntaxHighlighter.DOC_COMMENT_VALUE),
                AttributesDescriptor("SpjDoc CommentText", SpjSyntaxHighlighter.DOC_COMMENT_TEXT),
                AttributesDescriptor("Variable", SpjSyntaxHighlighter.VARIABLE),
                AttributesDescriptor("Argument", SpjSyntaxHighlighter.ARGUMENT),
                AttributesDescriptor("Operation sign", SpjSyntaxHighlighter.OPER),
                AttributesDescriptor("String", SpjSyntaxHighlighter.STRING),
                AttributesDescriptor("Number", SpjSyntaxHighlighter.NUMBER)
        )
    }
}