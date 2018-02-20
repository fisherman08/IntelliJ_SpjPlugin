package com.ky_proj.spjplugin.util

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.psi.SpjCallingFunction
import com.ky_proj.spjplugin.psi.SpjPsiUtil
import com.ky_proj.spjplugin.psi.SpjTypes

/**
 * Created on 2018/01/20.
 */
object SpjFunctionProvider{
    private var hiddenList :Array<PsiElement> = ArrayList<PsiElement>().toTypedArray()

    /**
     * 組み込み関数のリストを返す。
     * 高速化のためにシングルトンなので受け取った側でリストの中身を操作されると詰むので気をつけよう。
     */
    fun list(project: Project) :Array<PsiElement>{
        if(hiddenList.isNotEmpty())
            return hiddenList

        val file = SpjPsiUtil.createSpjFilewithResource(project, "/builtin/functions.spj")
        hiddenList = PsiTreeUtil.findChildrenOfType(file, SpjCallingFunction::class.java).toTypedArray()

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
