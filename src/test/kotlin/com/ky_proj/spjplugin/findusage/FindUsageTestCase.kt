package com.ky_proj.spjplugin.findusage

import com.intellij.testFramework.UsefulTestCase
import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Before
import org.junit.Test
/**
 * Created on 2018/01/30.
 */
class FindUsageTestCase : SpjTestCase(){
    override fun getTestDataPath(): String {
        return "${this.baseDir}/findusage"
    }

    @Before
    override fun setUp(){
        super.setUp()
    }

    @Test
    fun testFindUsage(){
        val usageInfos = myFixture.testFindUsages("def.spj", "usage.spj")
        UsefulTestCase.assertNotEmpty(usageInfos)
    }
}
