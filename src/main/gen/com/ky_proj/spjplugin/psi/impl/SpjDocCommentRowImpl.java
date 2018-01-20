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

public class SpjDocCommentRowImpl extends SpjNamedElementImpl implements SpjDocCommentRow {

  public SpjDocCommentRowImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SpjVisitor visitor) {
    visitor.visitDocCommentRow(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SpjVisitor) accept((SpjVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<SpjDocComTags> getDocComTagsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, SpjDocComTags.class);
  }

}
