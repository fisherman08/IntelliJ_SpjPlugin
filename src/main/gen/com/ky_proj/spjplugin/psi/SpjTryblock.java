// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface SpjTryblock extends PsiElement {

  @NotNull
  List<SpjArgs> getArgsList();

  @NotNull
  List<SpjArguments> getArgumentsList();

  @NotNull
  List<SpjCallingCommand> getCallingCommandList();

  @NotNull
  List<SpjCallingFunction> getCallingFunctionList();

  @NotNull
  List<SpjCallingProcedure> getCallingProcedureList();

  @NotNull
  List<SpjComCall> getComCallList();

  @NotNull
  List<SpjCondition> getConditionList();

  @NotNull
  List<SpjDocCommentRow> getDocCommentRowList();

  @NotNull
  List<SpjElseifstate> getElseifstateList();

  @NotNull
  List<SpjExceptblock> getExceptblockList();

  @NotNull
  List<SpjForblock> getForblockList();

  @NotNull
  List<SpjIfblock> getIfblockList();

  @NotNull
  List<SpjProcCall> getProcCallList();

  @NotNull
  List<SpjTryblock> getTryblockList();

  @NotNull
  List<SpjVars> getVarsList();

  @NotNull
  List<SpjWhileblock> getWhileblockList();

}
