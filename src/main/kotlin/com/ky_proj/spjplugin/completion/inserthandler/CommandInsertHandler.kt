package com.ky_proj.spjplugin.completion.inserthandler

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.psi.PsiElement

/**
 * Created on 2018/01/20.
 *
 * Command と Functionで使用されるInsertHandler
 */
class CommandInsertHandler : InsertHandler<LookupElement> {

    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        val editor = context.editor
        val node = (item.`object` as PsiElement).node
        //EditorModificationUtil.insertStringAtCaret(editor, "insert")
        editor.caretModel.moveToOffset(context.startOffset + node.firstChildNode.text.length + 1)
    }
}