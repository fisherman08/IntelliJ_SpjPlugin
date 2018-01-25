package com.ky_proj.spjplugin.reference

import com.intellij.codeInsight.completion.CompletionType
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class ReferenceTestCase :SpjTestCase(){
    override fun getTestDataPath(): String {
        return "${this.baseDir}/reference"
    }

    @Before
    override fun setUp(){
        super.setUp()
    }

    @Test
    fun testReference(){
        myFixture.configureByFile("/reference.spj")
        val element = myFixture.file?.findElementAt(myFixture.caretOffset)?.parent?.parent
        assertNotNull(element)
        if (element != null ){
            assertTrue(element.references[0].resolve()?.node?.elementType == SpjTypes.PROCEDURE_DEF)
        }
    }
}