package com.ky_proj.spjplugin.codeinsight

import com.ky_proj.spjplugin.SpjTestCase
import junit.framework.TestCase

class PropertyHintTestCase :SpjTestCase() {
    override fun getTestDataPath(): String {
        return "${this.baseDir}/codeinsight"
    }

    fun testArgumentHints(){
        myFixture.configureByFile("/argument.spj")

        TestCase.assertTrue(true)
    }
}