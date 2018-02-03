package com.ky_proj.spjplugin.NeoPrjFile.psi

/**
 * Created on 2018/02/03.
 */
import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.NeoPrjFile.language.NeoPrjFieLanguage
import org.jetbrains.annotations.NonNls

class NeoPrjFileElementType(@NonNls debugName: String) : IElementType(debugName, NeoPrjFieLanguage.INSTANCE)