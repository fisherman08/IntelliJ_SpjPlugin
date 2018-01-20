package com.ky_proj.spjplugin.completion

/**
 * Created on 2018/01/20.
 */
import com.intellij.codeInsight.completion.CompletionType
import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class CompletionTestCase :SpjTestCase(){
    override fun getTestDataPath(): String {
        return "${this.baseDir}/completion"
    }

    @Before
    override fun setUp(){
        super.setUp()
    }

    private fun getLookupElementStrings(filename :String) :Array<String>? {
        myFixture.configureByFile(filename)
        myFixture.complete(CompletionType.BASIC, 1)
        return myFixture.lookupElementStrings?.toTypedArray()
    }

    @Test
    fun testCommands(){
        val strings = getLookupElementStrings("/commands.spj")
        assertNotEquals(0 , strings?.size ?: 0)
        assertTrue(strings?.contains("RenameFile()") ?: false)
    }

    @Test
    fun testFunctions(){
        val strings = getLookupElementStrings("/function.spj")
        assertNotEquals(0, strings?.size ?: 0)
        assertTrue(strings?.contains("array.make()") ?: false)
    }

    @Test
    fun testFunctionsAsArgument(){
        val strings = getLookupElementStrings("/function_as_argument.spj")
        assertNotEquals(0, strings?.size ?: 0)
        assertTrue(strings?.contains("array.make()") ?: false)
    }

    @Test
    fun testVariables(){
        val strings = getLookupElementStrings("/variable.spj")
        assertNotEquals(0, strings?.size ?: 0)
        assertTrue(strings?.contains("\$stap") ?: false)
        assertFalse(strings?.contains("\$stap_from_form") ?: true)
    }

}