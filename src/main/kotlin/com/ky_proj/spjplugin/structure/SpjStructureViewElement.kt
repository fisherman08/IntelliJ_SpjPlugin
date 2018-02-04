package com.ky_proj.spjplugin.structure

/**
 * Created by Y.Kaneko on 2018/01/26.
 */

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ColoredItemPresentation
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.editor.colors.TextAttributesKey
import javax.swing.Icon


import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.ky_proj.spjplugin.icon.SpjIcon
import com.ky_proj.spjplugin.psi.SpjFile
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import com.sun.istack.internal.Nullable

import java.util.ArrayList

class SpjStructureViewElement(private val element: PsiElement) : StructureViewTreeElement, SortableTreeElement {

    override fun getValue(): Any {
        return element
    }

    override fun navigate(requestFocus: Boolean) {
        if (element is NavigationItem) {
            (element as NavigationItem).navigate(requestFocus)
        }
    }

    override fun canNavigate(): Boolean {
        return element is NavigationItem && (element as NavigationItem).canNavigate()
    }

    override fun canNavigateToSource(): Boolean {
        return element is NavigationItem && (element as NavigationItem).canNavigateToSource()
    }

    override fun getAlphaSortKey(): String {
        var result: String? = ""
        if (element is PsiNamedElement && element.name != null) {
            result = element.name
        }
        return if (result != null) result else ""
    }

    override fun getPresentation(): ItemPresentation {

        return object : ColoredItemPresentation {
            @Nullable
            override fun getTextAttributesKey(): TextAttributesKey? {
                return null
            }

            override fun getPresentableText(): String? {
                val node = element.node
                val elementType = node.elementType
                if (elementType === SpjTypes.PROCEDURE_DEF) {
                    return node.text
                } else if (element is SpjFile) {
                    return element.name
                }

                return ""
            }

            override fun getLocationString(): String? {
                return null
            }

            override fun getIcon(open: Boolean): Icon? {
                return if (element is SpjFile) {
                    SpjIcon.SPJ
                } else {
                    null
                }
            }

        }
    }

    override fun getChildren(): Array<TreeElement> {
        val treeElements = ArrayList<TreeElement>()
        if (element is SpjFile) {
            for (def in SpjProcedureProvider.listInFile(element, false)){
                treeElements.add(SpjStructureViewElement(def))
            }

        }
        return treeElements.toTypedArray()
    }
}