package com.ky_proj.spjplugin.NeoPrjFile.util

import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileFile
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileProperty
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileTypes

class NeoPrjFilePropertyFinder(internal var myFile: NeoPrjFileFile) {

    fun getValue(name_in: String): String {
        var result = ""
        val props = PsiTreeUtil.getChildrenOfType(myFile, NeoPrjFileProperty::class.java) ?: return result
        for (pros in props) {

            val key = pros.node.findChildByType(NeoPrjFileTypes.KEY) ?: continue

            if (key.text == name_in) {
                val value = pros.node.findChildByType(NeoPrjFileTypes.VALUE)
                if (value != null) {
                    result = value.text
                }
                break
            }
        }

        return result
    }
}
