package com.ky_proj.spjplugin.completion

/**
 * Created on 2018/01/20.
 */
import com.intellij.codeInsight.completion.CompletionType
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.util.SpjProcedureProvider
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

    /**
     * サンプルSPJファイルの<caret>の位置でcompleteを実行し、補完候補をSTringの配列で返す。
     * 補完候補が1つしかない場合はnullが返るので注意。
     */
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

    @Test
    fun testProcedure(){
        val strings = getLookupElementStrings("/procedure.spj")
        assertEquals(2, strings?.size ?: 0)
    }

    @Test
    fun testSystemProceduresIgnored(){
        myFixture.configureByFile("/procedure.system.spj")
        val definitions = SpjProcedureProvider.listInProject(myFixture.project, false)
        assertEquals(0, definitions.size)
    }

    @Test
    fun testProcedureAsFunction(){
        val strings = getLookupElementStrings("/procedure_as_function.spj")
        assertNotEquals(0, strings?.size ?: 0)
        assertTrue(strings?.contains("procedure_with_return()") ?: false)
        assertFalse(strings?.contains("procedure_without_return()") ?: true)
    }

    @Test
    fun testProcedureFromAnotherFile(){
        myFixture.configureByFiles("/procedure.spj", "/procedure.anotherfile.spj" )
        myFixture.complete(CompletionType.BASIC, 1)
        val elements = myFixture.lookupElementStrings?.toTypedArray()
        assertTrue(elements?.contains("test:from_another_file()") ?: false)
    }


    @Test
    fun testSpjDoc() {
        val strings = getLookupElementStrings("/spjdoc.spj")
        assertNotEquals(0, strings?.size ?: 0)
        assertTrue(strings?.contains("@example perform spjdoctest(arg1, arg2)") ?: false)
        assertTrue(strings?.contains("@param arg1 ") ?: false)
        assertTrue(strings?.contains("@param arg2 ") ?: false)
    }
}