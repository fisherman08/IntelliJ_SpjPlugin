package com.ky_proj.spjplugin.NeoPrjFile.util

import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Test

class NeoPrjFileFinderTest :SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/NeoPrjFile/util"
    }

    @Test
    fun testExistingPrjFile(){
        myFixture.configureByFile("propertyfinder.prj")
        val prjFiles = NeoPrjFileFinder(myFixture.project).allProjectFileNamesFromProjectRoot
        assertEquals(1, prjFiles.size)
    }

}
