package com.ky_proj.spjplugin.NeoPrjFile.filetype

import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileFile
import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Test

/**
 * Created on 2018/03/14.
 */

class NeoPrjFileFileTypeTestCase : SpjTestCase(){

    override fun getTestDataPath(): String {
        return "${this.baseDir}/NeoPrjFile/filetype"
    }

    @Test
    fun testFileTypeRecognition(){
        val file = getVirtualFile("sample.prj")
        assertEquals("Neo project file", file.fileType.name)
        assertTrue(file.fileType is NeoPrjFileFileType)
    }
}