package com.ky_proj.spjplugin.psi.impl

import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.parser.SpjParserTestCase
import com.ky_proj.spjplugin.psi.SpjPsiUtil
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjTreeUtil
import org.junit.Test

class SpjNamedImplTestCase : SpjParserTestCase() {

    @Test
    fun testRename(){
        val procedure_def = SpjPsiUtil.createSpjElement(project = this.project, content = "[func:to_rename(a, b)]", type = SpjTypes.PROCEDURE_DEF)
        val named_element = SpjNamedElementImpl(procedure_def.node)
        named_element.setName("func:renamed")
        assertEquals("func:renamed", named_element.name)
    }
}