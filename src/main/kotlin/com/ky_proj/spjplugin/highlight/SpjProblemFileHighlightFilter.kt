package com.ky_proj.spjplugin.highlight

import com.intellij.openapi.util.Condition
import com.intellij.openapi.vfs.VirtualFile
import com.ky_proj.spjplugin.filetype.SpjFileType

/**
 * Created on 2018/01/30.
 */
class SpjProblemFileHighlightFilter : Condition<VirtualFile> {
    override fun value(virtualFile: VirtualFile): Boolean {
        val fileType = virtualFile.fileType
        return fileType === SpjFileType.INSTANCE
    }
}