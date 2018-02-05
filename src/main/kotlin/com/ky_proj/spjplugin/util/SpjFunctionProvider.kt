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
    private var hiddenList :Array<PsiElement> = ArrayList<PsiElement>().toTypedArray()

    fun list(project: Project) :Array<PsiElement>{
        if(hiddenList.isNotEmpty())
            return hiddenList

        val file = SpjPsiUtil.createSpjFilewithResource(project, "/builtin/functions.spj")
        hiddenList = PsiTreeUtil.findChildrenOfType(file, SpjCallingFunction::class.java).toTypedArray()

        return hiddenList
    }
}
