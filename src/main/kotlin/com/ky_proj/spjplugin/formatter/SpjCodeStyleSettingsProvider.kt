package com.ky_proj.spjplugin.formatter

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.openapi.options.Configurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import com.ky_proj.spjplugin.language.SpjLanguage

class SpjCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings? {
        return SpjCodeStyleSettings(settings)
    }

    override fun getConfigurableDisplayName(): String? {
        return "SPJ"
    }

    override fun createSettingsPage(settings: CodeStyleSettings, originalSettings: CodeStyleSettings): Configurable {
        return object : CodeStyleAbstractConfigurable(settings, originalSettings, "spj") {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return SpjCodeStyleMainPanel(currentSettings, settings)
            }

            override fun getHelpTopic(): String? {
                return null
            }
        }
    }
    private class SpjCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) : TabbedLanguageCodeStylePanel(SpjLanguage.INSTANCE, currentSettings, settings)
}