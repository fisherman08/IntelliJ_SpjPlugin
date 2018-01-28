package com.ky_proj.spjplugin.commenter

import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Before
import org.junit.Test
import com.intellij.codeInsight.actions.OptimizeImportsAction.actionPerformedImpl
import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.ky_proj.spjplugin.filetype.SpjFileType


/**
 * Created on 2018/01/28.
 */

class CommenterTestCase() :SpjTestCase(){
    override fun getTestDataPath(): String {
        return "${this.baseDir}/commenter"
    }

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun testLineComment(){
        myFixture.configureByText(SpjFileType.INSTANCE, "<caret>array.make()")
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("# array.make()")
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("array.make()")
    }

}