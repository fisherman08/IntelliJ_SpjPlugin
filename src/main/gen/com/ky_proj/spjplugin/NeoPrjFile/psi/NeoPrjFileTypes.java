// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.NeoPrjFile.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.ky_proj.spjplugin.NeoPrjFile.psi.impl.*;

public interface NeoPrjFileTypes {

  IElementType PROPERTY = new NeoPrjFileTokenType("PROPERTY");

  IElementType COMMENT = new NeoPrjFileTokenType("COMMENT");
  IElementType KEY = new NeoPrjFileTokenType("KEY");
  IElementType SEPARATOR = new NeoPrjFileTokenType("SEPARATOR");
  IElementType VALUE = new NeoPrjFileTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == PROPERTY) {
        return new NeoPrjFilePropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
