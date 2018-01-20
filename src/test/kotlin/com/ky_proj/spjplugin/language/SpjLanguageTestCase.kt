package com.ky_proj.spjplugin.language

import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.filetype.SpjFileType
import org.junit.Ignore
import org.junit.Test

/**
 * Created on 2018/01/18.
 */

class SpjLanguageTestCase: SpjTestCase(){

    override fun getTestDataPath(): String {
        return "${this.baseDir}/language"
    }

    @Test
    fun testSpjLanguage(){
        val file = getVirtualFile("sample.spj")
        assertEquals("Language: spj", (file?.fileType as SpjFileType).language.toString())
    }
}