package com.ky_proj.spjplugin.setting

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.Nls

import javax.swing.*

class SpjSettingsConfigurable(private val project: Project) : SearchableConfigurable {
    private var settings: SpjSetting = SpjSetting(project)
    private var settingForm: SpjSettingForm? =  SpjSettingForm(settings)

    @Nls
    override fun getDisplayName(): String {
        return "SPJ Plugin"
    }

    override fun getHelpTopic(): String? {
        return null
    }

    override fun createComponent(): JComponent {
        settingForm = SpjSettingForm(settings)
        return settingForm!!.getRootPanel()
    }

    override fun isModified(): Boolean {
        return settingForm?.isModified() ?: false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        settings = settingForm?.getResult() ?:return
        settings.save()
    }

    override fun reset() {
        settingForm!!.setNeoVersion(settings)
        settingForm!!.setNeoProjectFile(settings)
    }

    override fun disposeUIResources() {
        settingForm = null
    }

    override fun getId(): String {
        return javaClass.name
    }

    override fun enableSearch(s: String?): Runnable? {
        return null
    }

}