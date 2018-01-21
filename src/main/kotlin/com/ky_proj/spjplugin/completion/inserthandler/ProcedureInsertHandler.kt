package com.ky_proj.spjplugin.completion.inserthandler

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElement
import com.ky_proj.spjplugin.psi.SpjTypes

/**
 * Created on 2018/01/20.
 */
class ProcedureInsertHandler : InsertHandler<LookupElement> {

    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        val editor = context.editor
        val procedureDef = item.`object` as PsiElement
        val procedureName = procedureDef.node.findChildByType(SpjTypes.PROCEDURE)?.text ?:return
        //EditorModificationUtil.insertStringAtCaret(editor, "insert")
        // 引数の定義があるならそこにcaretを移動する
        if(procedureDef.node.findChildByType(SpjTypes.ARGUMENTS) != null){
            editor.caretModel.moveToOffset(context.startOffset + procedureName.length + "(".length)
        }

    }
}
