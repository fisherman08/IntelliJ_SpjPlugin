package com.ky_proj.spjplugin.NeoPrjFile.psi

/**
 * Created on 2018/02/03.
 */
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.ky_proj.spjplugin.NeoPrjFile.filetype.NeoPrjFileFileType
import com.ky_proj.spjplugin.NeoPrjFile.language.NeoPrjFieLanguage

import javax.swing.*

class NeoPrjFileFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, NeoPrjFieLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return NeoPrjFileFileType.INSTANCE
    }

    override fun toString(): String {
        return "Neo Project Configuration File"
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }
}