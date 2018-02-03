// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.NeoPrjFile.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.ky_proj.spjplugin.NeoPrjFile.psi.NeoPrjFileTypes.*;
import com.ky_proj.spjplugin.NeoPrjFile.psi.*;

public class NeoPrjFilePropertyImpl extends NeoPrjFileNamedElementImpl implements NeoPrjFileProperty {

  public NeoPrjFilePropertyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NeoPrjFileVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NeoPrjFileVisitor) accept((NeoPrjFileVisitor)visitor);
    else super.accept(visitor);
  }

}
