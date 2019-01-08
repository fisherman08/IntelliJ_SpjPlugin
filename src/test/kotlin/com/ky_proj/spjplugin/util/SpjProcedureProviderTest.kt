package com.ky_proj.spjplugin.util

import com.ky_proj.spjplugin.SpjTestCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test

class SpjProcedureProviderTest : SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/util/spjproceduredef"
    }

    @Test
    fun testプロシージャを探す() = runBlocking{
        myFixture.configureByFiles("/1.spj", "/2.spj")
        val project = myFixture.project

        val result = SpjProcedureProvider.listInProject(project = project, is_only_return = false)

        assertEquals(4, result.size)
    }

    @Ignore
    fun testパフォーマンステスト() = runBlocking{
        // 500ファイル、100プロシージャから探す

        for(i in (1..1000)){
            var content = ""

            for(k in (1..100)){
                content += """
                    [proc_${i}_${k}]
                    section(hogege)
                    if(1 == 1) then
                      section(abv)
                    endif
                    aaa = replace_marker("select * from moge")
                    sql.query(db, aaa)
                    """.trimIndent()
            }

            myFixture.addFileToProject( "/test_$i.spj", content)
        }

        val project = myFixture.project

        val start = System.currentTimeMillis()
        val result = SpjProcedureProvider.listInProject(project = project, is_only_return = false)
        val end = System.currentTimeMillis()
        println(end - start)
        assertEquals(100000, result.size)
    }
}
