package com.ky_proj.spjplugin.completion

/**
 * Created on 2018/01/19.
 */
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.ky_proj.spjplugin.psi.*


class SpjBraceMatcher : PairedBraceMatcher {
    private val pairs = arrayOf(
            BracePair(SpjTypes.LPAR, SpjTypes.RPAR, true),
            BracePair(SpjTypes.LPARC, SpjTypes.RPARC, false),
            BracePair(SpjTypes.LBLO, SpjTypes.RBLO, false)
    )

    override fun getPairs(): Array<BracePair> {
        return pairs
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }
}
