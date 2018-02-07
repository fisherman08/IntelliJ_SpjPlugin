package com.ky_proj.spjplugin.setting

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.layout.panel
import com.ky_proj.spjplugin.NeoPrjFile.util.NeoPrjFileFinder
import javax.swing.JPanel

class SpjSettingForm(private val setting :SpjSetting) {
    private val rootPanel: JPanel? = null
    private val box_neo_version:   ComboBox<Float> = ComboBox<Float>(200)
    private val box_neo_prj_files: ComboBox<String> = ComboBox<String>(200)

    private val is_modified = false

    init {
        // NEOのバージョンを設定
        createNeoVersion()
        // プロジェクトファイルを設定
        createNeoProjectFile()
    }

    private fun createNeoVersion() {
        for (ver in arrayOf(2.0f, 3.0f, 4.0f)) {
            box_neo_version.addItem(ver)
        }

        this.setNeoVersion(setting)
    }

    fun setNeoVersion(setting: SpjSetting) {
        val itemCount = box_neo_version.getItemCount()
        // 初期選択

        val current_version = setting.getNeoVersion()
        for (i in 0 until itemCount) {
            val ver = box_neo_version.getItemAt(i) as Float
            // 初期選択
            if (ver == current_version) {
                box_neo_version.setSelectedItem(ver)
            }
        }
    }

    /**
     * プロジェクト内にいるprjファイルのリストを返す
     */
    private fun createNeoProjectFile() {
        val current_project = setting.getCurrentProject()

        // defaultで空文字を入れてあげる
        box_neo_prj_files.addItem("")

        val project_files = NeoPrjFileFinder(setting.getCurrentProject()).allProjectFileNamesFromProjectRoot
        for (project_file in project_files) {
            box_neo_prj_files.addItem(project_file)
        }

        this.setNeoProjectFile(setting)
    }

    fun setNeoProjectFile(setting: SpjSetting) {
        val itemCount = box_neo_prj_files.getItemCount()
        // 初期選択
        val project_file_path = setting.getProjecFilePath() ?: return

        for (i in 0 until itemCount) {
            val prj = box_neo_prj_files.getItemAt(i) as String
            // 初期選択
            if (prj == project_file_path) {
                box_neo_prj_files.setSelectedItem(prj)
            }
        }
    }

    fun getRootPanel(): JPanel {
        return panel {
            row {
                // child components
                label("Project NEO Version", 10, bold = true)
                box_neo_version()
            }
            row {
                label("Project NEO .prj File", 10, bold = true)
                box_neo_prj_files()
            }
        }
    }

    fun isModified(): Boolean {
        val original_version = setting.getNeoVersion()
        val current_version = box_neo_version.selectedItem as Float

        val original_path = setting.getProjecFilePath()
        val current_path = box_neo_prj_files.selectedItem as String


        return (original_version != current_version || original_path != current_path)
    }

    // 結果を取得
    fun getResult(): SpjSetting {
        // バージョン
        setting.setNeoVersion(box_neo_version.selectedItem as  Float)
        setting.setNeoProjectFilePath(box_neo_prj_files.selectedItem as String)

        return setting
    }

}