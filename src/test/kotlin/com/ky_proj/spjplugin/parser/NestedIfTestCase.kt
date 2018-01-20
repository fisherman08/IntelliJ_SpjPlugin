package com.ky_proj.spjplugin.parser

import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes
import org.junit.Before
import org.junit.Test

class NestedIfTestCase: SpjParserTestCase(){
    @Before
    override fun setUp(){
        super.setUp()
        baseFile = getPsiFile("nested_if.spj")
    }

    @Test
    fun testNestedIfBlock(){
        // 最上位
        val root = getNodeListOfTypes(baseFile, TokenSet.create(SpjTypes.IFBLOCK))
        assertEquals(1, root.size)

        // 一個下の階層
        val children = root[0].getChildren(TokenSet.create(SpjTypes.IFBLOCK))
        assertEquals(1, children.size)

        // さらにその下
        val grandChildren = children[0].getChildren(TokenSet.create(SpjTypes.IFBLOCK))
        assertEquals(2, grandChildren.size)
    }

    @Test
    fun testProcedureInsideNestedBlock(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.CALLING_PROCEDURE)))
    }
}