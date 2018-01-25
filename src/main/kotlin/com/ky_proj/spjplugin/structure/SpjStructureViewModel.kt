package com.ky_proj.spjplugin.structure

/**
 * Created by Y.Kaneko on 2018/01/26.
 */

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.psi.PsiFile
import com.ky_proj.spjplugin.psi.SpjFile
import com.ky_proj.spjplugin.psi.SpjProcedureDef

class SpjStructureViewModel(psiFile: PsiFile) : StructureViewModelBase(psiFile, SpjStructureViewElement(psiFile)), StructureViewModel.ElementInfoProvider {

    override fun getSorters(): Array<Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean {
        return false
    }

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
        return element.value is SpjProcedureDef
    }
}