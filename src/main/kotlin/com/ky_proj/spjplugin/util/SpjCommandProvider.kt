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

    private var hiddenList :Array<PsiElement> = ArrayList<PsiElement>().toTypedArray()

    fun list(project: Project) :Array<PsiElement>{
        if(hiddenList.isNotEmpty())
            return hiddenList

        val file = SpjPsiUtil.createSpjFilewithResource(project, "/builtin/commands.spj")
        hiddenList = PsiTreeUtil.findChildrenOfType(file, SpjCallingFunction::class.java).toTypedArray()

        return hiddenList
    }
}
