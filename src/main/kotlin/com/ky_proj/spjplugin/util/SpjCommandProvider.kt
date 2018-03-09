package com.ky_proj.spjplugin.util

import com.intellij.openapi.project.Project
import com.ky_proj.spjplugin.psi.SpjPsiUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.SpjCallingFunction
import com.ky_proj.spjplugin.psi.SpjTypes

/**
 * Created on 2018/01/19.
 */

object SpjCommandProvider{

    private var hiddenList :Array<PsiElement> = arrayOf()
    private var projectForList :Project? = null

    /**
     * 組み込みコマンドのリストを返す。
     * シングルトンなので受け取った側でリストの中身を操作されると詰むので気をつけよう。
     */
    fun list(project: Project) :Array<PsiElement>{
        if(hiddenList.isNotEmpty() && !(projectForList?.isDisposed ?:true))
            return hiddenList

        val file = SpjPsiUtil.createSpjFilewithResource(project, "/builtin/commands.spj")
        hiddenList = PsiTreeUtil.findChildrenOfType(file, SpjCallingFunction::class.java).toTypedArray()

        projectForList = project
        return hiddenList
    }

    fun getDefinitionByName(project :Project, name :String) :PsiElement?{
        for(elem in list(project)){
            val funName = elem.node.findChildByType(SpjTypes.FUNCTION)?.text ?: continue
            if(funName == name){
                return elem
            }
        }

        return null
    }
}
