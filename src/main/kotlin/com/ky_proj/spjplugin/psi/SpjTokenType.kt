package com.ky_proj.spjplugin.psi

import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.language.SpjLanguage
import org.jetbrains.annotations.NonNls

class SpjTokenType(@NonNls debugName: String) : IElementType(debugName, SpjLanguage.INSTANCE) {
    override fun toString(): String {
        return "SpjTokenType." + super.toString()
    }
}
