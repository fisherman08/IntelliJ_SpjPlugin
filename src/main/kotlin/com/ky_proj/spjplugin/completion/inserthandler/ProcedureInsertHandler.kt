package com.ky_proj.spjplugin.completion.inserthandler

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.mock.MockFileManager
import com.intellij.mock.MockPsiManager
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.PsiElementFactoryImpl
import com.ky_proj.spjplugin.filetype.SpjFileType
import com.ky_proj.spjplugin.psi.SpjFile

/**
 * Created on 2018/01/20.
 */
class ProeduleInsertHandler : InsertHandler<LookupElement> {

    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        val editor = context.getEditor()
        val completionChar = context.getCompletionChar()
        EditorModificationUtil.insertStringAtCaret(editor, "unko")
        editor.caretModel.moveToOffset(editor.caretModel.offset - 1)

        val manager = MockPsiManager(context.project)
        val file    = MockFileManager(manager)

        val factory = Factory(manager)


        //val comment = factory.createCommentFromText("", null)

        val type = SpjFileType.INSTANCE
        val result = PsiFileFactory.getInstance(context.project).createFileFromText("____sss_____", type, "local(abc)") as SpjFile
        val sss = ""

        val elementFactory = Factory(manager).createKeyword("aa")
        //val sample = SpjNamedElementImpl

        /*if (completionChar == ' ' || StringUtil.containsChar(myIgnoreOnChars, completionChar)) return
        val project = editor.project
        if (project != null) {
            if (!isCharAtSpace(editor)) {
                EditorModificationUtil.insertStringAtCaret(editor, " ")
                PsiDocumentManager.getInstance(project).commitDocument(editor.document)
            } else if (shouldOverwriteExistingSpace(editor)) {
                editor.caretModel.moveToOffset(editor.caretModel.offset + 1)
            }
            if (myTriggerAutoPopup) {
                AutoPopupController.getInstance(project).autoPopupMemberLookup(editor, null)
            }
        }*/
    }
}

class Factory(manager : MockPsiManager) : PsiElementFactoryImpl(manager){
}
