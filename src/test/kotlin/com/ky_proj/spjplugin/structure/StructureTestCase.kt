package com.ky_proj.spjplugin.structure

import com.intellij.codeInsight.completion.CompletionType
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class StructureTestCase :SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/structure"
    }

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun testStructureElement() {
        val elements = SpjStructureViewElement(getPsiFile("/structure.spj")?.originalElement!!)
        TestCase.assertEquals(3, elements.children.size)
    }
}