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

public class SpjProcCallImpl extends SpjNamedElementImpl implements SpjProcCall {

  public SpjProcCallImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SpjVisitor visitor) {
    visitor.visitProcCall(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SpjVisitor) accept((SpjVisitor)visitor);
    else super.accept(visitor);
  }

}
