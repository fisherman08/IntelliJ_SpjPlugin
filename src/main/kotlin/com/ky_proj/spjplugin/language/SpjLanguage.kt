package com.ky_proj.spjplugin.language

/**
 * Created on 2018/01/18.
 */
import com.intellij.lang.Language

class SpjLanguage private constructor() : Language("spj") {
    companion object {
        @JvmStatic val INSTANCE = SpjLanguage()
    }
}