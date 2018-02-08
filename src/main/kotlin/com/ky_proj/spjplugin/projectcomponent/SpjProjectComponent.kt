package com.ky_proj.spjplugin.projectcomponent

import com.intellij.notification.Notification
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.ky_proj.spjplugin.NeoPrjFile.filetype.NeoPrjFileFileType
import com.ky_proj.spjplugin.setting.SpjSetting
import com.ky_proj.spjplugin.setting.SpjSettingsConfigurable
import com.intellij.openapi.options.ShowSettingsUtil
import com.ky_proj.spjplugin.filetype.SpjFileType

/**
 * Created by Y.Kaneko on 2018/02/08.
 */
class SpjProjectComponent(private val myProject: Project) : ProjectComponent {
    /**
     * このプロジェクトにspjとprjが一つ以上含まれているか返す
     * @return
     */
    private val isSpjProject: Boolean
        get() {
            val spjFiles = FileTypeIndex.getFiles(SpjFileType.INSTANCE, GlobalSearchScope.allScope(myProject))
            val prjFiles = FileTypeIndex.getFiles(NeoPrjFileFileType.INSTANCE, GlobalSearchScope.allScope(myProject))

            return (spjFiles.isNotEmpty() && prjFiles.isNotEmpty())
        }

    override fun initComponent() {
        // TODO: insert component initialization logic here

    }

    override fun disposeComponent() {
        // TODO: insert component disposal logic here
    }

    override fun getComponentName(): String {
        return "SpjProjectComponent"
    }

    override fun projectOpened() {
        // called when project is opened
        StartupManager.getInstance(myProject).registerPostStartupActivity {
            // .prjファイルが指定されていなかったら怒る。
            notifyWhenProjectFileNotSpecified()
        }

    }

    override fun projectClosed() {
        // called when project is being closed
    }

    private fun notifyWhenProjectFileNotSpecified() {
        // spjとprjの存在しないプロジェクトなら何もしない
        if (!isSpjProject) {
            return
        }

        // settingを取得
        val setting = SpjSetting(myProject)

        // prjファイルが指定されていなかったらアラートを出す
        if (setting.getProjecFilePath() == null || setting.getProjecFilePath() == "") {
            Notifications.Bus.notify(
                    Notification("SpjPlugin",
                            "SpjPlugin: .prj file is not specified",
                            "<html>Please specify .prj file for the project.<br/> <a target='_blank' href='hofe'>Preference > Languages & Frameworks > SpjPlugin</a></html>",
                            NotificationType.WARNING,
                            NotificationListener { notification, hyperlinkEvent -> openSpjSetting() }),
                    myProject
            )

        }
    }

    private fun openSpjSetting() {
        ShowSettingsUtil.getInstance().showSettingsDialog(myProject, SpjSettingsConfigurable::class.java)
    }
}
