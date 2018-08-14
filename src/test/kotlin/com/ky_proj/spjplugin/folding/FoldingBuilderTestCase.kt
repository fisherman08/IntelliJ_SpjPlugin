package com.ky_proj.spjplugin.folding

import com.intellij.openapi.fileEditor.FileDocumentManager
import com.ky_proj.spjplugin.SpjTestCase
import org.junit.Before
import org.junit.Test

/**
 * Created on 2018/08/14.
 */
class FoldingBuilderTestCase :SpjTestCase(){
    override fun getTestDataPath(): String {
        return "${this.baseDir}/folding"
    }

    @Before
    override fun setUp(){
        super.setUp()
    }

    @Test
    fun testProcedureBlock(){
        val filename = "/procedure_block.spj"
        val virtual_file = getVirtualFile(filename)
        val elem = getPsiFile(filename)!!.originalElement

        val descriptors = SpjFoldingBuilder().buildFoldRegions(elem, FileDocumentManager.getInstance().getDocument(virtual_file)!! , false)

        assertEquals(2, descriptors.size)

    }

    @Test
    fun testIfBlocks() {
        val filename = "/if_block.spj"
        val virtual_file = getVirtualFile(filename)
        val elem = getPsiFile(filename)!!.originalElement

        val descriptors = SpjFoldingBuilder().buildFoldRegions(elem, FileDocumentManager.getInstance().getDocument(virtual_file)!! , false)

        assertEquals(4, descriptors.size)
    }
}