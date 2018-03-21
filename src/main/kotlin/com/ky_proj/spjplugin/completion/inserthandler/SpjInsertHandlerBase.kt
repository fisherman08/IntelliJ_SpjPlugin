package com.ky_proj.spjplugin.completion.inserthandler

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes

open class SpjInsertHandlerBase : InsertHandler<LookupElement> {

    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        execHandleInsert(context, item)
    }

    private fun execHandleInsert(context: InsertionContext, item: LookupElement) {
        val editor = context.editor
        val node = (item.`object` as PsiElement).node
        val commandName = node.findChildByType(TokenSet.create(SpjTypes.COM_CALL, SpjTypes.FUNCTION, SpjTypes.PROCEDURE))?.text ?: return

        // 引数の定義があるならそこにcaretを移動する
        val arguments = node.findChildByType(SpjTypes.ARGUMENTS) ?:return
        val startOffset = context.startOffset + commandName.length + "(".length
        editor.caretModel.moveToOffset(startOffset)

        // 引数に中身があるならそこにcaretを移動してさらに最初の引数を選択状態にする
        val firstArgument = arguments.findChildByType(TokenSet.create(SpjTypes.ARGS)) ?: return
        val endOffset = startOffset + firstArgument.text.length
        editor.selectionModel.setSelection(startOffset, endOffset)
        editor.caretModel.moveToOffset(endOffset)
    }
}