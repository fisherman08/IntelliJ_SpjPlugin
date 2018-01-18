package com.ky_proj.spjplugin.icon

import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.filetype.SpjFileType
import junit.framework.TestCase
import org.junit.Test

/**
 * Created on 2018/01/18.
 */
class SpjIconTestCase: SpjTestCase(){

    @Test
    fun testSpjIcon(){
        assertEquals(SpjIcon.FileName, "/icons/icon_spj.png")
    }
}