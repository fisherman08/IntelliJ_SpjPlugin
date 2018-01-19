package com.ky_proj.spjplugin.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

import com.ky_proj.spjplugin.filetype.SpjFileType
import com.ky_proj.spjplugin.language.SpjLanguage

import javax.swing.*

class SpjFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, SpjLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return SpjFileType.INSTANCE
    }

    override fun toString(): String {
        return "Spj File"
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }
}