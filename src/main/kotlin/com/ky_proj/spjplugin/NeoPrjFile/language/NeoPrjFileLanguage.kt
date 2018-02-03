package com.ky_proj.spjplugin.NeoPrjFile.language

/**
 * Created on 2018/02/03.
 */
/**
 * Created by Y.kaneko on 15/10/31.
 */
import com.intellij.lang.Language

class NeoPrjFieLanguage private constructor() : Language("prj") {
    companion object {
        @JvmStatic val INSTANCE = NeoPrjFieLanguage()
    }
}