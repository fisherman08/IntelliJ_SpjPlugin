package com.ky_proj.spjplugin.template

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

class SpjTemplateProvider : DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles(): Array<String> {
        return arrayOf("liveTemplates/SPJ")
    }

    override fun getHiddenLiveTemplateFiles(): Array<String>? {
        return ArrayList<String>().toTypedArray()
    }
}
