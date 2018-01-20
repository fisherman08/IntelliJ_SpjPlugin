package com.ky_proj.spjplugin.psi

import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.language.SpjLanguage
import org.jetbrains.annotations.NonNls

class SpjElementType(@NonNls debugName: String) : IElementType(debugName, SpjLanguage.INSTANCE)