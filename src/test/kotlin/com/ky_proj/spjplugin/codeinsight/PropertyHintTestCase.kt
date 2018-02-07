package com.ky_proj.spjplugin.codeinsight

import com.intellij.codeInsight.hints.InlayInfo
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjTreeUtil
import junit.framework.TestCase
import org.junit.Test

class PropertyHintTestCase :SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/codeinsight/parameterHints"
    }

    private fun getHints(filename : String) :List<InlayInfo>{
        val file = getPsiFile(filename)?: throw Exception("error")
        val args = SpjTreeUtil.findChildByTokenType(file.originalElement, TokenSet.create(SpjTypes.ARGUMENTS))[0]
        return SpjInlayParameterHintsProvider.getInstance().getParameterHints(args.psi)
    }


    @Test
    fun testProcedure(){
        val hints = getHints("/procedure.spj")
        TestCase.assertEquals(3, hints.size)
    }

    @Test
    fun testProcedureAsFuncion(){
        val hints = getHints("/procedure_as_func.spj")
        TestCase.assertEquals(4, hints.size)
    }

    @Test
    fun testFunction(){
        val hints = getHints("/function.spj")
        TestCase.assertEquals(2, hints.size)
    }

    @Test
    fun testCommand(){
        val hints = getHints("/command.spj")
        TestCase.assertEquals(2, hints.size)
    }


}