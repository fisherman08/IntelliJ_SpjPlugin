package com.ky_proj.spjplugin.NeoPrjFile.language

/**
 * Created on 2018/02/03.
 */
import com.intellij.lang.Language

class NeoPrjFieLanguage private constructor() : Language("prj") {
    companion object {
        @JvmStatic val INSTANCE = NeoPrjFieLanguage()
    }
}