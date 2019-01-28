// This is a generated file. Not intended for manual editing.
package com.ky_proj.spjplugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.ky_proj.spjplugin.psi.SpjTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class SpjParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ARGS) {
      r = args(b, 0);
    }
    else if (t == ARGUMENTS) {
      r = arguments(b, 0);
    }
    else if (t == CALLING_COMMAND) {
      r = calling_command(b, 0);
    }
    else if (t == CALLING_FUNCTION) {
      r = calling_function(b, 0);
    }
    else if (t == CALLING_PROCEDURE) {
      r = calling_procedure(b, 0);
    }
    else if (t == COM_CALL) {
      r = com_call(b, 0);
    }
    else if (t == CONDITION) {
      r = condition(b, 0);
    }
    else if (t == DOC_COM_TAGS) {
      r = doc_com_tags(b, 0);
    }
    else if (t == DOC_COMMENT_ROW) {
      r = doc_comment_row(b, 0);
    }
    else if (t == ELSEIFSTATE) {
      r = elseifstate(b, 0);
    }
    else if (t == EXCEPTBLOCK) {
      r = exceptblock(b, 0);
    }
    else if (t == FORBLOCK) {
      r = forblock(b, 0);
    }
    else if (t == IFBLOCK) {
      r = ifblock(b, 0);
    }
    else if (t == PROC_CALL) {
      r = proc_call(b, 0);
    }
    else if (t == PROCEDURE_BLOCK) {
      r = procedure_block(b, 0);
    }
    else if (t == PROCEDURE_DEF) {
      r = procedure_def(b, 0);
    }
    else if (t == TRYBLOCK) {
      r = tryblock(b, 0);
    }
    else if (t == VARS) {
      r = vars(b, 0);
    }
    else if (t == WHILEBLOCK) {
      r = whileblock(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return spjFile(b, l + 1);
  }

  /* ********************************************************** */
  // ARGUMENT
  public static boolean args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "args")) return false;
    if (!nextTokenIs(b, ARGUMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ARGUMENT);
    exit_section_(b, m, ARGS, r);
    return r;
  }

  /* ********************************************************** */
  // LPAR ((inside_arguments)|(inside_condition))* RPAR
  public static boolean arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments")) return false;
    if (!nextTokenIs(b, LPAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENTS, null);
    r = consumeToken(b, LPAR);
    p = r; // pin = 1
    r = r && report_error_(b, arguments_1(b, l + 1));
    r = p && consumeToken(b, RPAR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ((inside_arguments)|(inside_condition))*
  private static boolean arguments_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!arguments_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "arguments_1", c)) break;
    }
    return true;
  }

  // (inside_arguments)|(inside_condition)
  private static boolean arguments_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arguments_1_0_0(b, l + 1);
    if (!r) r = arguments_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (inside_arguments)
  private static boolean arguments_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (inside_condition)
  private static boolean arguments_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_condition(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // CALL com_call arguments
  public static boolean calling_command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "calling_command")) return false;
    if (!nextTokenIs(b, CALL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CALL);
    r = r && com_call(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, CALLING_COMMAND, r);
    return r;
  }

  /* ********************************************************** */
  // FUNCTION arguments
  public static boolean calling_function(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "calling_function")) return false;
    if (!nextTokenIs(b, FUNCTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FUNCTION);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, CALLING_FUNCTION, r);
    return r;
  }

  /* ********************************************************** */
  // PERFORM proc_call arguments
  public static boolean calling_procedure(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "calling_procedure")) return false;
    if (!nextTokenIs(b, PERFORM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PERFORM);
    r = r && proc_call(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, CALLING_PROCEDURE, r);
    return r;
  }

  /* ********************************************************** */
  // COMMAND_CALL
  public static boolean com_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "com_call")) return false;
    if (!nextTokenIs(b, COMMAND_CALL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMAND_CALL);
    exit_section_(b, m, COM_CALL, r);
    return r;
  }

  /* ********************************************************** */
  // SEPARATOR|COMMENT|doc_comment_row|STRING|
  //                        LPAR|RPAR|LPARC|RPARC|
  //                        OPER|ORAND|
  //                        COMMA|
  //                        PROCEDURE|
  //                        KEYWORD|
  //                        RETURN|
  //                        CATCH|
  //                        IMPORT|FUNCTION|NUMBER|
  //                        proc_call|com_call|vars|args|CRLF|DUMMY|BAD_CHARACTER
  static boolean common_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_item")) return false;
    boolean r;
    r = consumeToken(b, SEPARATOR);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = doc_comment_row(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, LPAR);
    if (!r) r = consumeToken(b, RPAR);
    if (!r) r = consumeToken(b, LPARC);
    if (!r) r = consumeToken(b, RPARC);
    if (!r) r = consumeToken(b, OPER);
    if (!r) r = consumeToken(b, ORAND);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, PROCEDURE);
    if (!r) r = consumeToken(b, KEYWORD);
    if (!r) r = consumeToken(b, RETURN);
    if (!r) r = consumeToken(b, CATCH);
    if (!r) r = consumeToken(b, IMPORT);
    if (!r) r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = proc_call(b, l + 1);
    if (!r) r = com_call(b, l + 1);
    if (!r) r = vars(b, l + 1);
    if (!r) r = args(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, DUMMY);
    if (!r) r = consumeToken(b, BAD_CHARACTER);
    return r;
  }

  /* ********************************************************** */
  // LPARC (inside_condition)+ RPARC
  public static boolean condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition")) return false;
    if (!nextTokenIs(b, LPARC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONDITION, null);
    r = consumeToken(b, LPARC);
    p = r; // pin = 1
    r = r && report_error_(b, condition_1(b, l + 1));
    r = p && consumeToken(b, RPARC) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (inside_condition)+
  private static boolean condition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = condition_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!condition_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "condition_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (inside_condition)
  private static boolean condition_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_condition(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DOC_COMMENT_TAG|DOC_COMMENT_TAG_DEPRECATED
  public static boolean doc_com_tags(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_com_tags")) return false;
    if (!nextTokenIs(b, "<doc com tags>", DOC_COMMENT_TAG, DOC_COMMENT_TAG_DEPRECATED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DOC_COM_TAGS, "<doc com tags>");
    r = consumeToken(b, DOC_COMMENT_TAG);
    if (!r) r = consumeToken(b, DOC_COMMENT_TAG_DEPRECATED);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DOC_COMMENT (doc_com_tags|DOC_COMMENT_VALUE|DOC_COMMENT_TEXT)*
  public static boolean doc_comment_row(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_comment_row")) return false;
    if (!nextTokenIs(b, DOC_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOC_COMMENT);
    r = r && doc_comment_row_1(b, l + 1);
    exit_section_(b, m, DOC_COMMENT_ROW, r);
    return r;
  }

  // (doc_com_tags|DOC_COMMENT_VALUE|DOC_COMMENT_TEXT)*
  private static boolean doc_comment_row_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_comment_row_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!doc_comment_row_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "doc_comment_row_1", c)) break;
    }
    return true;
  }

  // doc_com_tags|DOC_COMMENT_VALUE|DOC_COMMENT_TEXT
  private static boolean doc_comment_row_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_comment_row_1_0")) return false;
    boolean r;
    r = doc_com_tags(b, l + 1);
    if (!r) r = consumeToken(b, DOC_COMMENT_VALUE);
    if (!r) r = consumeToken(b, DOC_COMMENT_TEXT);
    return r;
  }

  /* ********************************************************** */
  // ELSE ELSEIF  [THEN] (condition)+
  public static boolean elseifstate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseifstate")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ELSEIFSTATE, null);
    r = consumeTokens(b, 2, ELSE, ELSEIF);
    p = r; // pin = 2
    r = r && report_error_(b, elseifstate_2(b, l + 1));
    r = p && elseifstate_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [THEN]
  private static boolean elseifstate_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseifstate_2")) return false;
    consumeToken(b, THEN);
    return true;
  }

  // (condition)+
  private static boolean elseifstate_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseifstate_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = elseifstate_3_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!elseifstate_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "elseifstate_3", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (condition)
  private static boolean elseifstate_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseifstate_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = condition(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EXCEPT (arguments)*
  public static boolean exceptblock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptblock")) return false;
    if (!nextTokenIs(b, EXCEPT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EXCEPTBLOCK, null);
    r = consumeToken(b, EXCEPT);
    p = r; // pin = 1
    r = r && exceptblock_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (arguments)*
  private static boolean exceptblock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptblock_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!exceptblock_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "exceptblock_1", c)) break;
    }
    return true;
  }

  // (arguments)
  private static boolean exceptblock_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exceptblock_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // FOR (arguments)+ (inside_block)* ENDFOR
  public static boolean forblock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forblock")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FORBLOCK, null);
    r = consumeToken(b, FOR);
    p = r; // pin = 1
    r = r && report_error_(b, forblock_1(b, l + 1));
    r = p && report_error_(b, forblock_2(b, l + 1)) && r;
    r = p && consumeToken(b, ENDFOR) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (arguments)+
  private static boolean forblock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forblock_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = forblock_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!forblock_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "forblock_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (arguments)
  private static boolean forblock_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forblock_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (inside_block)*
  private static boolean forblock_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forblock_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!forblock_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "forblock_2", c)) break;
    }
    return true;
  }

  // (inside_block)
  private static boolean forblock_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "forblock_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IF [THEN] (condition) inside_block* ((elseifstate) inside_block* (ENDIF)* )* (ELSE [THEN] inside_block*)* ENDIF
  public static boolean ifblock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFBLOCK, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, ifblock_1(b, l + 1));
    r = p && report_error_(b, ifblock_2(b, l + 1)) && r;
    r = p && report_error_(b, ifblock_3(b, l + 1)) && r;
    r = p && report_error_(b, ifblock_4(b, l + 1)) && r;
    r = p && report_error_(b, ifblock_5(b, l + 1)) && r;
    r = p && consumeToken(b, ENDIF) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [THEN]
  private static boolean ifblock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_1")) return false;
    consumeToken(b, THEN);
    return true;
  }

  // (condition)
  private static boolean ifblock_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = condition(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // inside_block*
  private static boolean ifblock_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!inside_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifblock_3", c)) break;
    }
    return true;
  }

  // ((elseifstate) inside_block* (ENDIF)* )*
  private static boolean ifblock_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ifblock_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifblock_4", c)) break;
    }
    return true;
  }

  // (elseifstate) inside_block* (ENDIF)*
  private static boolean ifblock_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ifblock_4_0_0(b, l + 1);
    r = r && ifblock_4_0_1(b, l + 1);
    r = r && ifblock_4_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (elseifstate)
  private static boolean ifblock_4_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_4_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = elseifstate(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // inside_block*
  private static boolean ifblock_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_4_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!inside_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifblock_4_0_1", c)) break;
    }
    return true;
  }

  // (ENDIF)*
  private static boolean ifblock_4_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_4_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, ENDIF)) break;
      if (!empty_element_parsed_guard_(b, "ifblock_4_0_2", c)) break;
    }
    return true;
  }

  // (ELSE [THEN] inside_block*)*
  private static boolean ifblock_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ifblock_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifblock_5", c)) break;
    }
    return true;
  }

  // ELSE [THEN] inside_block*
  private static boolean ifblock_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && ifblock_5_0_1(b, l + 1);
    r = r && ifblock_5_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [THEN]
  private static boolean ifblock_5_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_5_0_1")) return false;
    consumeToken(b, THEN);
    return true;
  }

  // inside_block*
  private static boolean ifblock_5_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifblock_5_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!inside_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ifblock_5_0_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // args|COMMENT|STRING|NUMBER|CRLF|COMMA|BAD_CHARACTER|calling_function|arguments
  static boolean inside_arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inside_arguments")) return false;
    boolean r;
    r = args(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, BAD_CHARACTER);
    if (!r) r = calling_function(b, l + 1);
    if (!r) r = arguments(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // arguments|calling_function|condition|ifblock|common_item|forblock|whileblock|elseifstate|calling_procedure|calling_command|tryblock|exceptblock|ELSE|IF|ELSEIF|THEN
  static boolean inside_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inside_block")) return false;
    boolean r;
    r = arguments(b, l + 1);
    if (!r) r = calling_function(b, l + 1);
    if (!r) r = condition(b, l + 1);
    if (!r) r = ifblock(b, l + 1);
    if (!r) r = common_item(b, l + 1);
    if (!r) r = forblock(b, l + 1);
    if (!r) r = whileblock(b, l + 1);
    if (!r) r = elseifstate(b, l + 1);
    if (!r) r = calling_procedure(b, l + 1);
    if (!r) r = calling_command(b, l + 1);
    if (!r) r = tryblock(b, l + 1);
    if (!r) r = exceptblock(b, l + 1);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, ELSEIF);
    if (!r) r = consumeToken(b, THEN);
    return r;
  }

  /* ********************************************************** */
  // (condition)|vars|COMMENT|ARGUMENT|NUMBER|STRING|CRLF|OPER|ORAND|BAD_CHARACTER|calling_function
  static boolean inside_condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inside_condition")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_condition_0(b, l + 1);
    if (!r) r = vars(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, ARGUMENT);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = consumeToken(b, OPER);
    if (!r) r = consumeToken(b, ORAND);
    if (!r) r = consumeToken(b, BAD_CHARACTER);
    if (!r) r = calling_function(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (condition)
  private static boolean inside_condition_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inside_condition_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = condition(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // procedure_block|item_p
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = procedure_block(b, l + 1);
    if (!r) r = item_p(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // arguments|calling_function|condition|ifblock|common_item|forblock|whileblock|elseifstate|tryblock|exceptblock|calling_procedure|calling_command|
  //                                         IF|ELSE|ENDIF|THEN|FOR|ENDFOR|WHILE|ENDWHILE|CATCH|ELSEIF|PERFORM|CALL|FUNCTION|TRY|EXCEPT|FINALLY|ENDTRY
  static boolean item_p(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_p")) return false;
    boolean r;
    r = arguments(b, l + 1);
    if (!r) r = calling_function(b, l + 1);
    if (!r) r = condition(b, l + 1);
    if (!r) r = ifblock(b, l + 1);
    if (!r) r = common_item(b, l + 1);
    if (!r) r = forblock(b, l + 1);
    if (!r) r = whileblock(b, l + 1);
    if (!r) r = elseifstate(b, l + 1);
    if (!r) r = tryblock(b, l + 1);
    if (!r) r = exceptblock(b, l + 1);
    if (!r) r = calling_procedure(b, l + 1);
    if (!r) r = calling_command(b, l + 1);
    if (!r) r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, ENDIF);
    if (!r) r = consumeToken(b, THEN);
    if (!r) r = consumeToken(b, FOR);
    if (!r) r = consumeToken(b, ENDFOR);
    if (!r) r = consumeToken(b, WHILE);
    if (!r) r = consumeToken(b, ENDWHILE);
    if (!r) r = consumeToken(b, CATCH);
    if (!r) r = consumeToken(b, ELSEIF);
    if (!r) r = consumeToken(b, PERFORM);
    if (!r) r = consumeToken(b, CALL);
    if (!r) r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, TRY);
    if (!r) r = consumeToken(b, EXCEPT);
    if (!r) r = consumeToken(b, FINALLY);
    if (!r) r = consumeToken(b, ENDTRY);
    return r;
  }

  /* ********************************************************** */
  // PROCEDURE_CALL
  public static boolean proc_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "proc_call")) return false;
    if (!nextTokenIs(b, PROCEDURE_CALL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROCEDURE_CALL);
    exit_section_(b, m, PROC_CALL, r);
    return r;
  }

  /* ********************************************************** */
  // procedure_def (item_p)*
  public static boolean procedure_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "procedure_block")) return false;
    if (!nextTokenIs(b, LBLO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PROCEDURE_BLOCK, null);
    r = procedure_def(b, l + 1);
    p = r; // pin = 1
    r = r && procedure_block_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (item_p)*
  private static boolean procedure_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "procedure_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!procedure_block_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "procedure_block_1", c)) break;
    }
    return true;
  }

  // (item_p)
  private static boolean procedure_block_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "procedure_block_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = item_p(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LBLO PROCEDURE (arguments)* RBLO
  public static boolean procedure_def(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "procedure_def")) return false;
    if (!nextTokenIs(b, LBLO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBLO, PROCEDURE);
    r = r && procedure_def_2(b, l + 1);
    r = r && consumeToken(b, RBLO);
    exit_section_(b, m, PROCEDURE_DEF, r);
    return r;
  }

  // (arguments)*
  private static boolean procedure_def_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "procedure_def_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!procedure_def_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "procedure_def_2", c)) break;
    }
    return true;
  }

  // (arguments)
  private static boolean procedure_def_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "procedure_def_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean spjFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "spjFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "spjFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // TRY (inside_block)* (exceptblock)* (inside_block)* [FINALLY] (inside_block)* ENDTRY
  public static boolean tryblock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock")) return false;
    if (!nextTokenIs(b, TRY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TRYBLOCK, null);
    r = consumeToken(b, TRY);
    p = r; // pin = 1
    r = r && report_error_(b, tryblock_1(b, l + 1));
    r = p && report_error_(b, tryblock_2(b, l + 1)) && r;
    r = p && report_error_(b, tryblock_3(b, l + 1)) && r;
    r = p && report_error_(b, tryblock_4(b, l + 1)) && r;
    r = p && report_error_(b, tryblock_5(b, l + 1)) && r;
    r = p && consumeToken(b, ENDTRY) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (inside_block)*
  private static boolean tryblock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tryblock_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tryblock_1", c)) break;
    }
    return true;
  }

  // (inside_block)
  private static boolean tryblock_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (exceptblock)*
  private static boolean tryblock_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tryblock_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tryblock_2", c)) break;
    }
    return true;
  }

  // (exceptblock)
  private static boolean tryblock_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = exceptblock(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (inside_block)*
  private static boolean tryblock_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tryblock_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tryblock_3", c)) break;
    }
    return true;
  }

  // (inside_block)
  private static boolean tryblock_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [FINALLY]
  private static boolean tryblock_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_4")) return false;
    consumeToken(b, FINALLY);
    return true;
  }

  // (inside_block)*
  private static boolean tryblock_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tryblock_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tryblock_5", c)) break;
    }
    return true;
  }

  // (inside_block)
  private static boolean tryblock_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tryblock_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // VARIABLE
  public static boolean vars(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "vars")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VARIABLE);
    exit_section_(b, m, VARS, r);
    return r;
  }

  /* ********************************************************** */
  // WHILE (condition)+ (inside_block)* ENDWHILE
  public static boolean whileblock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileblock")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, WHILEBLOCK, null);
    r = consumeToken(b, WHILE);
    p = r; // pin = 1
    r = r && report_error_(b, whileblock_1(b, l + 1));
    r = p && report_error_(b, whileblock_2(b, l + 1)) && r;
    r = p && consumeToken(b, ENDWHILE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (condition)+
  private static boolean whileblock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileblock_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = whileblock_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!whileblock_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "whileblock_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (condition)
  private static boolean whileblock_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileblock_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = condition(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (inside_block)*
  private static boolean whileblock_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileblock_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!whileblock_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "whileblock_2", c)) break;
    }
    return true;
  }

  // (inside_block)
  private static boolean whileblock_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "whileblock_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = inside_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
