package com.ky_proj.spjplugin.NeoPrjFile.filetype

/**
 * Created on 2018/02/03.
 */
import com.intellij.openapi.fileTypes.LanguageFileType
import com.ky_proj.spjplugin.NeoPrjFile.icons.NeoPrjFileIcons
import com.ky_proj.spjplugin.NeoPrjFile.language.NeoPrjFieLanguage
import javax.swing.*

class NeoPrjFileFileType private constructor() : LanguageFileType(NeoPrjFieLanguage.INSTANCE) {

    override fun getName(): String {
        return "Neo project file"
    }

    override fun getDescription(): String {
        return "101NEO project configuration file"
    }

    override fun getDefaultExtension(): String {
        return "prj"
    }

    override fun getIcon(): Icon? {
        return NeoPrjFileIcons.FILE
    }

    companion object {
        val INSTANCE = NeoPrjFileFileType()
    }
}