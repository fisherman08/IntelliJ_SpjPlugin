// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface SpjArguments extends PsiElement {

  @NotNull
  List<SpjArgs> getArgsList();

  @NotNull
  List<SpjArguments> getArgumentsList();

  @NotNull
  List<SpjCallingFunction> getCallingFunctionList();

  @NotNull
  List<SpjCondition> getConditionList();

  @NotNull
  List<SpjVars> getVarsList();

}
