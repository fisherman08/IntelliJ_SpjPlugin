package com.ky_proj.spjplugin.NeoPrjFile.util

import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileFile
import com.ky_proj.spjplugin.SpjTestCase
import junit.framework.TestCase
import org.junit.Test

class NeoPrjFilePropertyFinderTest :SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/NeoPrjFile/util"
    }

    @Test
    fun testExistingProperty(){
        val file = getPsiFile("propertyfinder.prj")
        val finder = NeoPrjFilePropertyFinder(file!! as NeoPrjFileFile)
        TestCase.assertEquals("spj", finder.getValue("SpecDir"))
    }

    @Test
    fun testNotExistingProperty(){
        val file = getPsiFile("propertyfinder.prj")
        val finder = NeoPrjFilePropertyFinder(file!! as NeoPrjFileFile)
        TestCase.assertEquals("", finder.getValue("TheMeaningOfLife"))
    }
}