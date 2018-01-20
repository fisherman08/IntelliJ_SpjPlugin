package com.ky_proj.spjplugin.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.ky_proj.spjplugin.filetype.SpjFileType
import java.net.URL

/**
 * Created on 2018/01/20.
 */

object SpjPsiUtil {

    fun createSpjFilewithResource(project :Project, filename : String) :SpjFile{
        val url : URL? = this.javaClass.getResource(filename)
        return  createSpjFilewithString(project, url?.readText() ?: "")
    }

    fun createSpjFilewithString(project :Project, string :String) : SpjFile{
        val type = SpjFileType.INSTANCE
        return PsiFileFactory.getInstance(project).createFileFromText("______com.ky_proj.spjplugin.defaults.command.spj_____", type, string) as SpjFile
    }
}