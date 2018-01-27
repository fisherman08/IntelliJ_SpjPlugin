package com.ky_proj.spjplugin.annotator

/**
 * Created on 2018/01/27.
 */
import com.intellij.codeInsight.completion.CompletionType
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.structure.SpjStructureViewElement
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
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
    fun testAnnotator() {
        myFixture.configureByFile("/annotator.spj")
        myFixture.checkHighlighting(true, true, true)
    }
}