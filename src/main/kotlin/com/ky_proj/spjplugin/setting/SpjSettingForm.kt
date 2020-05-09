package com.ky_proj.spjplugin.setting

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.layout.panel
import com.intellij.util.ui.UIUtil
import com.ky_proj.spjplugin.NeoPrjFile.util.NeoPrjFileFinder
import javax.swing.JPanel

class SpjSettingForm(private val setting :SpjSetting) {
    private val boxNeoVersion:   ComboBox<Float> = ComboBox<Float>(200)
    private val boxNeoPrjFiles: ComboBox<String> = ComboBox<String>(200)

    init {
        // NEOのバージョンを設定
        createNeoVersion()
        // プロジェクトファイルを設定
        createNeoProjectFile()
    }

    private fun createNeoVersion() {
        for (ver in arrayOf(2.0f, 3.0f, 4.0f)) {
            boxNeoVersion.addItem(ver)
        }

        this.setNeoVersion(setting)
    }

    fun setNeoVersion(setting: SpjSetting) {
        val itemCount = boxNeoVersion.getItemCount()

        // 初期選択
        val projectNeoVersion = setting.getNeoVersion()
        for (i in 0 until itemCount) {
            val ver = boxNeoVersion.getItemAt(i) as Float
            // 初期選択
            if (ver == projectNeoVersion) {
                boxNeoVersion.setSelectedItem(ver)
            }
        }
    }

    /**
     * プロジェクト内にいるprjファイルのリストを返す
     */
    private fun createNeoProjectFile() {
        // defaultで空文字を入れてあげる
        boxNeoPrjFiles.addItem("")

        val allPrjFiles = NeoPrjFileFinder(setting.getCurrentProject()).allProjectFileNamesFromProjectRoot
        for (priFile in allPrjFiles) {
            boxNeoPrjFiles.addItem(priFile)
        }

        this.setNeoProjectFile(setting)
    }

    fun setNeoProjectFile(setting: SpjSetting) {
        val itemCount = boxNeoPrjFiles.itemCount
        // 初期選択
        val prjFilePath = setting.getProjectFilePath() ?: return

        for (i in 0 until itemCount) {
            val prj = boxNeoPrjFiles.getItemAt(i) as String
            // 初期選択
            if (prj == prjFilePath) {
                boxNeoPrjFiles.selectedItem = prj
            }
        }
    }

    fun getRootPanel(): JPanel {
        return panel {
            row {
                // child components
                label("Project NEO Version", UIUtil.ComponentStyle.REGULAR, bold = true)
                boxNeoVersion()
            }
            row {
                label("Project NEO .prj File", UIUtil.ComponentStyle.REGULAR, bold = true)
                boxNeoPrjFiles()
            }
        }
    }

    fun isModified(): Boolean {
        val originalVersion = setting.getNeoVersion()
        val currentVersion = boxNeoVersion.selectedItem as Float

        val originalPath = setting.getProjectFilePath()
        val currentPath = boxNeoPrjFiles.selectedItem as String


        return (originalVersion != currentVersion || originalPath != currentPath)
    }

    // 結果を取得
    fun getResult(): SpjSetting {
        // バージョン
        setting.setNeoVersion(boxNeoVersion.selectedItem as  Float)
        setting.setNeoProjectFilePath(boxNeoPrjFiles.selectedItem as String)

        return setting
    }

}
