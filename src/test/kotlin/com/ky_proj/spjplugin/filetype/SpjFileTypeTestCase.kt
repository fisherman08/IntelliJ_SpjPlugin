package com.ky_proj.spjplugin.filetype

import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Test

/**
 * Created on 2018/01/18.
 */

class SpjFileTypeTestCase: SpjTestCase(){

    override fun getTestDataPath(): String {
        return "${this.baseDir}/filetype"
    }

    @Test
    fun testFileTypeRecognition(){
        val file = getVirtualFile("sample.spj")
        assertEquals("Spj File", file.fileType.name)
    }
}