package com.ky_proj.spjplugin.NeoPrjFile.highlight

/**
 * Created on 2018/02/03.
 */
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class NeoPrjFileSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return NeoPrjFileSyntaxHighlighter()
    }
}