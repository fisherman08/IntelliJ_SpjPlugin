package com.ky_proj.spjplugin.NeoPrjFile.icons

import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.filetype.SpjFileType
import junit.framework.TestCase
import org.junit.Test

/**
 * Created on 2018/03/17.
 */
class NeoPrjFileIconTestCase : SpjTestCase(){

    override fun getTestDataPath(): String {
        return "${this.baseDir}"
    }

    @Test
    fun testIcon(){
        assertEquals("/icons/icon_prj.png", NeoPrjFileIcons.FileName)
    }
}