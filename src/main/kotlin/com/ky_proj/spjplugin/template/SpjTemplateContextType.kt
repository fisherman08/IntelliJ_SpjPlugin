package com.ky_proj.spjplugin.template

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

class SpjTemplateContextType protected constructor() : TemplateContextType("SPJ", "Spj") {

    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        return file.name.endsWith(".spj")
    }
}