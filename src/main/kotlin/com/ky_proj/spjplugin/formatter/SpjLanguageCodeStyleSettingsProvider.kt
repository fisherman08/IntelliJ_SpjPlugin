package com.ky_proj.spjplugin.formatter

import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.ky_proj.spjplugin.language.SpjLanguage
import com.ky_proj.spjplugin.util.SpjUtil


class SpjLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language {
        return SpjLanguage.INSTANCE
    }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: LanguageCodeStyleSettingsProvider.SettingsType) {
        if (settingsType == LanguageCodeStyleSettingsProvider.SettingsType.SPACING_SETTINGS) {
            consumer.showStandardOptions("SPACE_AFTER_COMMA")
            consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS")
            consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Around equals")

            consumer.showStandardOptions("SPACE_AROUND_LOGICAL_OPERATORS")
            consumer.renameStandardOption("SPACE_AROUND_LOGICAL_OPERATORS", "Space around logical operators")
            //  consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Separator");
        } else if (settingsType == LanguageCodeStyleSettingsProvider.SettingsType.BLANK_LINES_SETTINGS) {
            // consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
        } else if (settingsType == LanguageCodeStyleSettingsProvider.SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
            // consumer.showStandardOptions("KEEP_LINE_BREAKS");
        }
    }

    override fun getDefaultCommonSettings(): CommonCodeStyleSettings? {
        val defaultSettings = CommonCodeStyleSettings(SpjLanguage.INSTANCE)

        val indentOptions = defaultSettings.initIndentOptions()

        indentOptions.USE_TAB_CHARACTER = false
        indentOptions.INDENT_SIZE = 4
        indentOptions.TAB_SIZE = 4
        indentOptions.CONTINUATION_INDENT_SIZE = 8

        return defaultSettings
    }

    override fun getCodeSample(settingsType: LanguageCodeStyleSettingsProvider.SettingsType): String {
        try {
            return SpjUtil.GET_SAMPLE_SPJ_FILE.sample
        } catch (e: Exception) {
            return ""
        }

    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor? {
        return SmartIndentOptionsEditor()
    }

}
