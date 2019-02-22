package com.ky_proj.spjplugin.codeinsight.hint
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjTreeUtil
import org.junit.Test

class SpjImplementationTextSelectionerTest: SpjTestCase() {

    override fun getTestDataPath(): String {
        return ""
    }

    @Test
    fun testStartOffset(){
        val file_content = """
        [test()]
        ## @example perform test()
        ## @deprecated
        """.trimIndent()

        myFixture.configureByText("test.spj", file_content)
        val file = myFixture.file
        val def = SpjTreeUtil.findChildByTokenType(file, SpjTypes.PROCEDURE_DEF)

        val selectioner = SpjImplementationTextSelectioner()
        assertEquals(0, selectioner.getTextStartOffset(def.first().psi))
    }

    @Test
    fun testEndOffseNormal(){
        val file_content = """
        aa = array.make()
        [test()]
        ## @example perform test()
        ## @deprecated

        """.trimIndent()

        myFixture.configureByText("test.spj", file_content)
        val file = myFixture.file
        val def = SpjTreeUtil.findChildByTokenType(file, SpjTypes.CALLING_FUNCTION)

        val selectioner = SpjImplementationTextSelectioner()
        assertEquals(17, selectioner.getTextEndOffset(def.first().psi))
    }

    @Test
    fun testEndOffseProcedureDef(){
        val file_content = """
        [test()]
        ## @example perform test()
        ## @deprecated
        section(aaa)

        """.trimIndent()

        myFixture.configureByText("test.spj", file_content)
        val file = myFixture.file
        val def = SpjTreeUtil.findChildByTokenType(file, SpjTypes.PROCEDURE_DEF)

        val selectioner = SpjImplementationTextSelectioner()
        assertEquals(63, selectioner.getTextEndOffset(def.first().psi))
    }
}