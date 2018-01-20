package com.ky_proj.spjplugin.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Created by Y.Kaneko on 2015/11/21.
 */

interface SpjNamedElement : PsiNameIdentifierOwner {
    fun getNameIdentifier(element: SpjProcedureDef): PsiElement
}