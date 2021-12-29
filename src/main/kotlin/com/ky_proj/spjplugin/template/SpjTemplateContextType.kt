package com.ky_proj.spjplugin.template

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.codeInsight.template.TemplateActionContext;

class SpjTemplateContextType protected constructor() : TemplateContextType("SPJ", "Spj") {

    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return templateActionContext.file.name.endsWith(".spj")
    }
}
