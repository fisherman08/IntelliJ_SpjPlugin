package com.ky_proj.spjplugin.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange

import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.language.SpjLanguage

import com.ky_proj.spjplugin.psi.SpjTypes.*

class SpjFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val commonSettings = formattingContext.codeStyleSettings.getCommonSettings(SpjLanguage.INSTANCE)
        val spacingBuilder = createSpacingBuilder(commonSettings)

        val block = SpjBlock(formattingContext.psiElement.node, Wrap.createWrap(WrapType.NORMAL, false), Alignment.createAlignment(), spacingBuilder)

        return FormattingModelProvider.createFormattingModelForPsiFile(formattingContext.psiElement.containingFile, block, formattingContext.codeStyleSettings)
    }


    private fun createSpacingBuilder(settings: CommonCodeStyleSettings): SpacingBuilder {

        val codeStyleSettings = CodeStyleSettings()

        return SpacingBuilder(codeStyleSettings, SpjLanguage.INSTANCE)
                .before(COMMA).spaceIf(settings.SPACE_BEFORE_COMMA)
                .after(COMMA).spaceIf(settings.SPACE_AFTER_COMMA)
                .before(SpjTypes.SEPARATOR).spaceIf(settings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .after(SpjTypes.SEPARATOR).spaceIf(settings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
                .before(SpjTypes.ORAND).spaceIf(settings.SPACE_AROUND_LOGICAL_OPERATORS)
                .after(SpjTypes.ORAND).spaceIf(settings.SPACE_AROUND_LOGICAL_OPERATORS)
                .before(SpjTypes.OPER).spaceIf(settings.SPACE_AROUND_LOGICAL_OPERATORS)
                .after(SpjTypes.OPER).spaceIf(settings.SPACE_AROUND_LOGICAL_OPERATORS)
    }

    override fun getRangeAffectingIndent(psiFile: PsiFile, i: Int, astNode: ASTNode): TextRange? {
        return null
    }
}
