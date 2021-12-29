package com.ky_proj.spjplugin.annotator

/**
 * Created on 2018/01/27.
 */
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.setting.SpjSetting
import org.junit.Before
import org.junit.Test

class AnnotatorTestCase :SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/annotator"
    }

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun test未定義プロシージャ() {
        val file_content = """
        perform ununun()
        """.trimIndent()
        myFixture.configureByText("test.spj", file_content)
        val highlights = myFixture.doHighlighting()
        assertEquals(1, highlights.size)
        assertEquals("ERROR", highlights[0].severity.name)
        assertEquals("Undefined procedure", highlights[0].description)
    }


    @Test
    fun testプロシージャの引数が少ない() {
        val file_content = """
        perform test(1 ,2)

        [test(a, b, c)]
        """.trimIndent()
        myFixture.configureByText("test.spj", file_content)
        val highlights = myFixture.doHighlighting()
        assertEquals(1, highlights.size)
        assertEquals("WARNING", highlights[0].severity.name)
        assertEquals("Number of arguments is smaller than definition", highlights[0].description)
    }


    @Test
    fun testプロシージャの引数が多い() {
        val file_content = """
        perform test(1 ,2, 3, 4)

        [test(a, b, c)]
        """.trimIndent()
        myFixture.configureByText("test.spj", file_content)
        SpjSetting(myFixture.project).setNeoVersion(4.0f)

        val highlights = myFixture.doHighlighting()
        assertEquals(1, highlights.size)
        //assertEquals("WARNING", highlights[0].severity.name)
        //assertEquals("Too many arguments", highlights[0].description)
    }

    @Test
    fun testリターンのないプロシージャを関数形式で呼び出す() {
        val file_content = """
        result = test()

        [test()]
        section(aaa)
        """.trimIndent()
        myFixture.configureByText("test.spj", file_content)
        SpjSetting(myFixture.project).setNeoVersion(4.0f)

        val highlights = myFixture.doHighlighting()
        assertEquals(1, highlights.size)
        assertEquals("ERROR", highlights[0].severity.name)
        assertEquals("This procedure does not include 'return'", highlights[0].description)
    }

    @Test
    fun test非推奨のプロシージャを呼んでいる() {
        val file_content = """
        perform test()

        [test()]
        ## @example perform test()
        ## @deprecated
        """.trimIndent()
        myFixture.configureByText("test.spj", file_content)
        SpjSetting(myFixture.project).setNeoVersion(4.0f)

        val highlights = myFixture.doHighlighting()
        assertEquals(1, highlights.size)
        assertEquals("WARNING", highlights[0].severity.name)
        assertEquals("This procedure is deprecated", highlights[0].description)
    }


    @Test
    fun test非推奨のプロシージャを関数形式て呼んでいる() {
        val file_content = """
        result = test()

        [test()]
        return(1)
        ## @example perform test()
        ## @deprecated
        """.trimIndent()
        myFixture.configureByText("test.spj", file_content)
        SpjSetting(myFixture.project).setNeoVersion(4.0f)

        val highlights = myFixture.doHighlighting()
        assertEquals(1, highlights.size)
        assertEquals("WARNING", highlights[0].severity.name)
        assertEquals("This procedure is deprecated", highlights[0].description)
    }
}
