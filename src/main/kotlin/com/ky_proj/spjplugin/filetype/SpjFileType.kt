package com.ky_proj.spjplugin.filetype

/**
 * Created on 2018/01/18.
 */

import com.intellij.openapi.fileTypes.LanguageFileType
import com.ky_proj.spjplugin.icon.SpjIcon
import com.ky_proj.spjplugin.language.SpjLanguage
import javax.swing.*

class SpjFileType private constructor() : LanguageFileType(SpjLanguage.INSTANCE) {

    override fun getName(): String {
        return "Spj File"
    }

    override fun getDescription(): String {
        return "101NEO Spj language file"
    }

    override fun getDefaultExtension(): String {
        return "spj"
    }

    override fun getIcon(): Icon? {
        return SpjIcon.FILE
    }

    companion object {
        @JvmStatic val INSTANCE = SpjFileType()
    }
}