package com.ky_proj.spjplugin.util

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.SpjCallingFunction
import com.ky_proj.spjplugin.psi.SpjPsiUtil

/**
 * Created on 2018/01/20.
 */
object SpjFunctionProvider{
    fun list(project: Project) :Array<PsiElement>{
        val file = SpjPsiUtil.createSpjFilewithResource(project, "/builtin/functions.spj")
        return PsiTreeUtil.findChildrenOfType(file, SpjCallingFunction::class.java).toTypedArray()
    }
}
