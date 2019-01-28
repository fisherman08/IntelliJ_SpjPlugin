// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.ky_proj.spjplugin.psi.SpjTypes.*;
import com.ky_proj.spjplugin.psi.*;

public class SpjProcedureBlockImpl extends SpjNamedElementImpl implements SpjProcedureBlock {

  public SpjProcedureBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SpjVisitor visitor) {
    visitor.visitProcedureBlock(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SpjVisitor) accept((SpjVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<SpjArgs> getArgsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjArgs.class);
  }

  @Override
  @NotNull
  public List<SpjArguments> getArgumentsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjArguments.class);
  }

  @Override
  @NotNull
  public List<SpjCallingCommand> getCallingCommandList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjCallingCommand.class);
  }

  @Override
  @NotNull
  public List<SpjCallingFunction> getCallingFunctionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjCallingFunction.class);
  }

  @Override
  @NotNull
  public List<SpjCallingProcedure> getCallingProcedureList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjCallingProcedure.class);
  }

  @Override
  @NotNull
  public List<SpjComCall> getComCallList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjComCall.class);
  }

  @Override
  @NotNull
  public List<SpjCondition> getConditionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjCondition.class);
  }

  @Override
  @NotNull
  public List<SpjDocCommentRow> getDocCommentRowList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjDocCommentRow.class);
  }

  @Override
  @NotNull
  public List<SpjElseifstate> getElseifstateList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjElseifstate.class);
  }

  @Override
  @NotNull
  public List<SpjExceptblock> getExceptblockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjExceptblock.class);
  }

  @Override
  @NotNull
  public List<SpjForblock> getForblockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjForblock.class);
  }

  @Override
  @NotNull
  public List<SpjIfblock> getIfblockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjIfblock.class);
  }

  @Override
  @NotNull
  public List<SpjProcCall> getProcCallList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjProcCall.class);
  }

  @Override
  @NotNull
  public SpjProcedureDef getProcedureDef() {
    return findNotNullChildByClass(SpjProcedureDef.class);
  }

  @Override
  @NotNull
  public List<SpjTryblock> getTryblockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjTryblock.class);
  }

  @Override
  @NotNull
  public List<SpjVars> getVarsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjVars.class);
  }

  @Override
  @NotNull
  public List<SpjWhileblock> getWhileblockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjWhileblock.class);
  }

}
