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

public class SpjArgumentsImpl extends SpjNamedElementImpl implements SpjArguments {

  public SpjArgumentsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SpjVisitor visitor) {
    visitor.visitArguments(this);
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
  public List<SpjCallingFunction> getCallingFunctionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjCallingFunction.class);
  }

  @Override
  @NotNull
  public List<SpjCondition> getConditionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjCondition.class);
  }

  @Override
  @NotNull
  public List<SpjVars> getVarsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjVars.class);
  }

}
