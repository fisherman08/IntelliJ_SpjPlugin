package com.ky_proj.spjplugin.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.filetype.SpjFileType
import com.ky_proj.spjplugin.util.SpjTreeUtil
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

    fun createProcedureCallwithName(project: Project, name: String): IElementType {
        val file = createSpjFilewithString(project, name)
        return file.firstChild as IElementType
    }

    fun createSpjElement(project: Project, content: String, type: IElementType) :PsiElement {
        if(type == SpjTypes.PROC_CALL) {
            val new_calling_procedure = SpjPsiUtil.createSpjElement(project = project, content = "perform ${content}()", type = SpjTypes.CALLING_PROCEDURE)
            return SpjTreeUtil.findChildByTokenType(new_calling_procedure, SpjTypes.PROC_CALL).first().psi
        }

        if(type == SpjTypes.FUNCTION) {
            val new_calling_function = SpjPsiUtil.createSpjElement(project = project, content = "${content}()", type = SpjTypes.CALLING_FUNCTION)
            return SpjTreeUtil.findChildByTokenType(new_calling_function, SpjTypes.FUNCTION).first().psi
        }


        val file = createSpjFilewithString(project = project, string = content)
        val elems = SpjTreeUtil.findChildByTokenType(element = file, tokenset = TokenSet.create(type))
        return if(elems.size > 0) {
            elems.first().psi
        } else {
            file
        }
    }
}