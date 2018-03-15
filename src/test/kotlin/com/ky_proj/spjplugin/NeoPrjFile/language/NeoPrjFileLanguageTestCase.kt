package com.ky_proj.spjplugin.NeoPrjFile.language

import com.ky_proj.spjplugin.NeoPrjFile.filetype.NeoPrjFileFileType
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.filetype.SpjFileType
import org.junit.Ignore
import org.junit.Test

/**
 * Created on 2018/03/16.
 */

class NeoPrjFileLanguageTestCase : SpjTestCase(){

    override fun getTestDataPath(): String {
        return "${this.baseDir}/NeoPrjFile/language"
    }

    @Test
    fun testLanguage(){
        val file = getVirtualFile("sample.prj")
        assertEquals("Language: prj", (file.fileType as NeoPrjFileFileType).language.toString())
    }
}