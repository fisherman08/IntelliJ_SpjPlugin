package com.ky_proj.spjplugin.NeoPrjFile.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.ky_proj.spjplugin.NeoPrjFile.filetype.NeoPrjFileFileType
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileFile
import com.ky_proj.spjplugin.setting.SpjSetting

import java.util.ArrayList
import java.util.regex.Pattern

/**
 * Created by Y.Kaneko on 2017/03/19.
 */
class NeoPrjFileFinder(private val current_project: Project) {

    val projectFileFromSettings: NeoPrjFileFile?
        get() {

            val setting = SpjSetting(current_project)
            val prj_file_path = setting.getProjecFilePath()

            return this.getProjectFileFromPathString(prj_file_path)
        }

    val allProjectFiles: Collection<VirtualFile>
        get() = FileTypeIndex.getFiles(NeoPrjFileFileType.INSTANCE, GlobalSearchScope.allScope(current_project))

    // ページごとに取得する
    //filename = filename.replaceAll("¥¥", "¥¥¥¥");
    val allProjectFileNamesFromProjectRoot: Array<String>
        get() {
            val virtualFiles = allProjectFiles
            val project_root = current_project.presentableUrl ?: ""

            val tmp_result = ArrayList<String>()

            for (virtualFile in virtualFiles) {
                val file = PsiManager.getInstance(current_project).findFile(virtualFile) as NeoPrjFileFile? ?: continue

                var filename = virtualFile.presentableUrl
                filename = filename.replaceFirst(Pattern.quote(project_root).toRegex(), "")
                filename = filename.substring(1)
                tmp_result.add(filename)

            }

            return tmp_result.toTypedArray()
        }

    fun getProjectFileFromPathString(path: String): NeoPrjFileFile? {
        var result: NeoPrjFileFile? = null
        val virtualFiles = allProjectFiles

        val project_root = current_project?.presentableUrl ?: ""

        for (virtualFile in virtualFiles) {
            // ページごとに取得する
            val file = PsiManager.getInstance(current_project).findFile(virtualFile) as NeoPrjFileFile? ?: continue

            var filename = virtualFile.presentableUrl
            //filename = filename.replaceAll("¥¥", "¥¥¥¥");
            filename = filename.replaceFirst(Pattern.quote(project_root).toRegex(), "")
            filename = filename.substring(1)
            if (filename == path) {
                // 設定で保存されているファイル名と一致したらそいつ
                result = file
                break
            }

        }

        return result
    }
}
