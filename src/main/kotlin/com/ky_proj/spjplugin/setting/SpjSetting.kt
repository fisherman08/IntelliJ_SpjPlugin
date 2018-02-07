package com.ky_proj.spjplugin.setting

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.ky_proj.spjplugin.NeoPrjFile.util.NeoPrjFileFinder
import com.ky_proj.spjplugin.NeoPrjFile.util.NeoPrjFilePropertyFinder

class SpjSetting(val project :Project){

    private val KEY_NEO_VERSION = "com.ky_proj.spjplugin.neo_version"
    private val KEY_PROJECT_FILE = "com.ky_proj.spjplugin.project_file"

    private var neo_version: Float = 4.0f
    private var projectFilePath :String = ""

    init {
        val setting = this

        // ApplicationManagerのReadActionとして実行しないとIDEエラーが出る😡(IDEA 2017.1以降)
        ApplicationManager.getApplication().runReadAction {

            // 現在のプロパティを取得
            val props = PropertiesComponent.getInstance(project)

            // neoバージョン
            neo_version      = props.getFloat(KEY_NEO_VERSION, 4.0f)

            // プロジェクトファイル
            val tmpProjectFilePath = props.getValue(KEY_PROJECT_FILE, "")
            projectFilePath = tmpProjectFilePath

            if (tmpProjectFilePath != "") {
                val prjfile = NeoPrjFileFinder(project).getProjectFileFromPathString(tmpProjectFilePath)
                if (prjfile == null) {
                    // 設定されていたプロジェクトファイルが消えていたら設定値を削除する
                    setNeoProjectFilePath("")
                    setting.save()
                }
            }
        }
    }

    fun isEnhanceMode(): Boolean {
        val setting = SpjSetting(project)
        val neoVersion = setting.getNeoVersion()

        // バージョンが2だとEnhanceModeは存在しない
        if (neoVersion < 3.0) {
            return false
        }

        // バージョン4以上なら常にEnhanceModeとして動く
        if (neoVersion >= 4.0) {
            return true
        }

        // バージョン3の時はプロジェクトファイルを見る
        val prjfile = NeoPrjFileFinder(project)?.projectFileFromSettings ?: return true

        return  (NeoPrjFilePropertyFinder(prjfile).getValue("EnhanceMode") == "true")

    }

    fun getNeoVersion(): Float {
        return neo_version
    }

    fun getProjecFilePath(): String {
        return projectFilePath
    }

    fun setNeoVersion(version: Float) {
        neo_version = version
    }

    fun setNeoProjectFilePath(path: String) {
        projectFilePath = path
    }

    fun save() {
        val props = PropertiesComponent.getInstance(project)
        props.setValue(KEY_NEO_VERSION, neo_version, 4.0f)
        props.setValue(KEY_PROJECT_FILE, projectFilePath, "")
    }

    fun getCurrentProject(): Project {
        return project
    }
}