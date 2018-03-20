package com.ky_proj.spjplugin.NeoPrjFile.util

import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileFile
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileProperty
import com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileTypes

class NeoPrjFilePropertyFinder(private val myFile: NeoPrjFileFile) {

    fun getValue(propertyName: String): String {

        val allProperties = PsiTreeUtil.getChildrenOfType(myFile, NeoPrjFileProperty::class.java) ?: return ""
        for (property in allProperties) {

            val key = property.node.findChildByType(NeoPrjFileTypes.KEY) ?: continue
            if (key.text != propertyName) {
                // keyが違う
                continue
            }

            // keyが一致していたらvalueを返す
            return property.node.findChildByType(NeoPrjFileTypes.VALUE)?.text ?: ""
        }

        // 一致するkeyが存在しない
        return ""
    }
}
