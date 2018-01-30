package com.ky_proj.spjplugin.highlight

import com.ky_proj.spjplugin.SpjTestCase
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

/**
 * Created on 2018/01/30.
 */
class ProblemFileTestCase :SpjTestCase(){
    override fun getTestDataPath(): String {
        return "${this.baseDir}/highlight"
    }

    @Before
    override fun setUp(){
        super.setUp()
    }

    @Test
    fun testProblemFile(){
        val file = getVirtualFile("/problem_file.spj")
        val highlightFiler = SpjProblemFileHighlightFilter()
        TestCase.assertTrue(highlightFiler.value(file))
    }
}