package com.ky_proj.spjplugin.util

import com.intellij.openapi.project.Project
import com.ky_proj.spjplugin.psi.SpjPsiUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.SpjCallingFunction
/**
 * Created on 2018/01/19.
 */

object SpjCommandProvider{
    fun list(project: Project) :Array<PsiElement>{
        val file = SpjPsiUtil.createSpjFilewithResource(project, "/builtin/commands.spj")
        return PsiTreeUtil.findChildrenOfType(file, SpjCallingFunction::class.java).toTypedArray()
    }
}
