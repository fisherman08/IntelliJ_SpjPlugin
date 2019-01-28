// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.ky_proj.spjplugin.psi.impl.*;

public interface SpjTypes {

  IElementType ARGS = new SpjElementType("ARGS");
  IElementType ARGUMENTS = new SpjElementType("ARGUMENTS");
  IElementType CALLING_COMMAND = new SpjElementType("CALLING_COMMAND");
  IElementType CALLING_FUNCTION = new SpjElementType("CALLING_FUNCTION");
  IElementType CALLING_PROCEDURE = new SpjElementType("CALLING_PROCEDURE");
  IElementType COM_CALL = new SpjElementType("COM_CALL");
  IElementType CONDITION = new SpjElementType("CONDITION");
  IElementType DOC_COMMENT_ROW = new SpjElementType("DOC_COMMENT_ROW");
  IElementType DOC_COM_TAGS = new SpjElementType("DOC_COM_TAGS");
  IElementType ELSEIFSTATE = new SpjElementType("ELSEIFSTATE");
  IElementType EXCEPTBLOCK = new SpjElementType("EXCEPTBLOCK");
  IElementType FORBLOCK = new SpjElementType("FORBLOCK");
  IElementType IFBLOCK = new SpjElementType("IFBLOCK");
  IElementType PROCEDURE_BLOCK = new SpjElementType("PROCEDURE_BLOCK");
  IElementType PROCEDURE_DEF = new SpjElementType("PROCEDURE_DEF");
  IElementType PROC_CALL = new SpjElementType("PROC_CALL");
  IElementType TRYBLOCK = new SpjElementType("TRYBLOCK");
  IElementType VARS = new SpjElementType("VARS");
  IElementType WHILEBLOCK = new SpjElementType("WHILEBLOCK");

  IElementType ARGUMENT = new SpjTokenType("ARGUMENT");
  IElementType BAD_CHARACTER = new SpjTokenType("BAD_CHARACTER");
  IElementType CALL = new SpjTokenType("CALL");
  IElementType CATCH = new SpjTokenType("CATCH");
  IElementType COMMA = new SpjTokenType("COMMA");
  IElementType COMMAND_CALL = new SpjTokenType("COMMAND_CALL");
  IElementType COMMENT = new SpjTokenType("COMMENT");
  IElementType CRLF = new SpjTokenType("CRLF");
  IElementType DOC_COMMENT = new SpjTokenType("DOC_COMMENT");
  IElementType DOC_COMMENT_TAG = new SpjTokenType("DOC_COMMENT_TAG");
  IElementType DOC_COMMENT_TAG_DEPRECATED = new SpjTokenType("DOC_COMMENT_TAG_DEPRECATED");
  IElementType DOC_COMMENT_TEXT = new SpjTokenType("DOC_COMMENT_TEXT");
  IElementType DOC_COMMENT_VALUE = new SpjTokenType("DOC_COMMENT_VALUE");
  IElementType DUMMY = new SpjTokenType("DUMMY");
  IElementType ELSE = new SpjTokenType("ELSE");
  IElementType ELSEIF = new SpjTokenType("ELSEIF");
  IElementType ENDFOR = new SpjTokenType("ENDFOR");
  IElementType ENDIF = new SpjTokenType("ENDIF");
  IElementType ENDTRY = new SpjTokenType("ENDTRY");
  IElementType ENDWHILE = new SpjTokenType("ENDWHILE");
  IElementType EXCEPT = new SpjTokenType("EXCEPT");
  IElementType FINALLY = new SpjTokenType("FINALLY");
  IElementType FOR = new SpjTokenType("FOR");
  IElementType FUNCTION = new SpjTokenType("FUNCTION");
  IElementType IF = new SpjTokenType("IF");
  IElementType IMPORT = new SpjTokenType("IMPORT");
  IElementType KEYWORD = new SpjTokenType("KEYWORD");
  IElementType LBLO = new SpjTokenType("LBLO");
  IElementType LPAR = new SpjTokenType("LPAR");
  IElementType LPARC = new SpjTokenType("LPARC");
  IElementType NUMBER = new SpjTokenType("NUMBER");
  IElementType OPER = new SpjTokenType("OPER");
  IElementType ORAND = new SpjTokenType("ORAND");
  IElementType PERFORM = new SpjTokenType("PERFORM");
  IElementType PROCEDURE = new SpjTokenType("PROCEDURE");
  IElementType PROCEDURE_CALL = new SpjTokenType("PROCEDURE_CALL");
  IElementType RBLO = new SpjTokenType("RBLO");
  IElementType RETURN = new SpjTokenType("RETURN");
  IElementType RPAR = new SpjTokenType("RPAR");
  IElementType RPARC = new SpjTokenType("RPARC");
  IElementType SEPARATOR = new SpjTokenType("SEPARATOR");
  IElementType STRING = new SpjTokenType("STRING");
  IElementType THEN = new SpjTokenType("THEN");
  IElementType TRY = new SpjTokenType("TRY");
  IElementType VARIABLE = new SpjTokenType("VARIABLE");
  IElementType WHILE = new SpjTokenType("WHILE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGS) {
        return new SpjArgsImpl(node);
      }
      else if (type == ARGUMENTS) {
        return new SpjArgumentsImpl(node);
      }
      else if (type == CALLING_COMMAND) {
        return new SpjCallingCommandImpl(node);
      }
      else if (type == CALLING_FUNCTION) {
        return new SpjCallingFunctionImpl(node);
      }
      else if (type == CALLING_PROCEDURE) {
        return new SpjCallingProcedureImpl(node);
      }
      else if (type == COM_CALL) {
        return new SpjComCallImpl(node);
      }
      else if (type == CONDITION) {
        return new SpjConditionImpl(node);
      }
      else if (type == DOC_COMMENT_ROW) {
        return new SpjDocCommentRowImpl(node);
      }
      else if (type == DOC_COM_TAGS) {
        return new SpjDocComTagsImpl(node);
      }
      else if (type == ELSEIFSTATE) {
        return new SpjElseifstateImpl(node);
      }
      else if (type == EXCEPTBLOCK) {
        return new SpjExceptblockImpl(node);
      }
      else if (type == FORBLOCK) {
        return new SpjForblockImpl(node);
      }
      else if (type == IFBLOCK) {
        return new SpjIfblockImpl(node);
      }
      else if (type == PROCEDURE_BLOCK) {
        return new SpjProcedureBlockImpl(node);
      }
      else if (type == PROCEDURE_DEF) {
        return new SpjProcedureDefImpl(node);
      }
      else if (type == PROC_CALL) {
        return new SpjProcCallImpl(node);
      }
      else if (type == TRYBLOCK) {
        return new SpjTryblockImpl(node);
      }
      else if (type == VARS) {
        return new SpjVarsImpl(node);
      }
      else if (type == WHILEBLOCK) {
        return new SpjWhileblockImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
