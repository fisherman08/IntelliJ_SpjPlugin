package com.ky_proj.spjplugin.completion.inserthandler

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes

/**
 * Created on 2018/01/20.
 */
class ProcedureInsertHandler :SpjInsertHandlerBase(){
    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        super.handleInsert(context, item)
    }
}
