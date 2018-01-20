package com.ky_proj.spjplugin.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.ky_proj.spjplugin.psi.SpjTypes;
import com.intellij.psi.TokenType;
import java.util.Stack;

%%

%class SpjLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%{
	private int after_argument;
	private Stack<Integer> stack = new Stack<Integer>();

    public void yypushstate(int newState) {
      stack.push(yystate());

      yybegin(newState);
    }

    public void yypopstate() {
        Integer prev = YYINITIAL;

        if(!stack.empty()){
            prev = stack.pop();
        }
      yybegin(prev);

    }
%}

%eof{

%eof}


CRLF= \n|\r|\r\n|¥n|¥r|¥r¥n

HEX_DIGIT = [a-fA-F0-9]
DOUBLE_QUOTE = \x22
SINGLE_QUOTE = \x27
COMMON_ESCAPE = ( [nrt0\n\r\\] | "x" {HEX_DIGIT} {2} | "u" {HEX_DIGIT} {4} | "U" {HEX_DIGIT} {8} )
STRING = {DOUBLE_QUOTE} ( [^\"\\] | "\\" ( [^\"] | {DOUBLE_QUOTE} | {SINGLE_QUOTE} | {CRLF} | {COMMON_ESCAPE}) )* {DOUBLE_QUOTE}

NORMAL_CHARACTER = [^CRLF\(\),]
WHITE_SPACE=[\ \t\f]
PROCEDURE=\[[^\r\n]*\]
SEPARATOR=\=
KEYWORD=("local"|"section"|
"session"|"end"|"break"|"commit"|
"rollback"|"transaction"|"compute"|"put"|
"remove"|"output"|"print"|"sendmail"|
"fork"|"last"|"next"|"throw"
|"catch"|"trap"|"endfor"|"endwhile"){1}
RETURN=("return"){1}

IF=("if")
ELSE=("else")
ENDIF=("endif")
THEN=("then")
FOR=("for")
ENDFOR=("endfor")
WHILE=("while")
ENDWHILE=("endwhile")
CATCH=("catch")
PERFORM="perform"
CALL="call"

TRY=("try")
EXCEPT=("except")
FINALLY=("finally")
ENDTRY=("endtry")

IMPORT=("%include")

OPER=("="|"=="|"<"|">"|"!="|"<="|">="|"<>")
COMPUTER=(";"|"+"|"-"|"*"|"/"|"%"|"?"|":")
ORAND=("&&"|"||")


VARIABLE=[$?]*[^#(),\"\n\r\t\f\\)\[\]\ \=<>!&|]
NUMBER=[-]?(0|[0-9])*[.]?[0-9]+

/*PROCEDURE_CALL=[^\ \t\f=\r\n]+\(.*\)*/
PROCEDURE_CALL=[^\ \t\f=\r\n()]+

START_OF_DOC_COMMENT=("##")
DOC_COMMENT_TAG=("@")[^\ \t\f\r\n]*
DOC_COMMENT_TAG_EXAMPLE=("@example")
DOC_COMMENT_TEXT=[^\ \t\f\r\n]

END_OF_LINE_COMMENT=("#")[^#\r\n]*


%state WAITING_VALUE
%state CALL_COMMAND
%state CALL_KEYWORD
%state DEF_PROCEDURE
%state CALL_PROCEDURE
%state INSIDE_ARGUMENTS
%state INSIDE_NESTED_ARGUMENTS
%state CALL_CONDITION
%state INSIDE_CONDITION
%state INSIDE_FOR_ARGUMENTS
%state INSIDE_IMPORT
%state AFTER_ELSE
%state AFTER_EXCEPT
%state INSIDE_DOC_COMMENT
%state INSIDE_DOC_COMMENT_PARAM

%%

/* ↓
    左側の正規表現に一致したら右側のメソッドが実行される
*/
<YYINITIAL>{WHITE_SPACE}+                                   { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
<YYINITIAL>\[                                               { yypushstate(DEF_PROCEDURE); return SpjTypes.LBLO;}
<YYINITIAL>\]                                               { return SpjTypes.RBLO;}

<YYINITIAL>\(                                               { yypushstate(INSIDE_CONDITION); return SpjTypes.LPARC;}
<YYINITIAL>\)                                               { yypopstate();return SpjTypes.RPARC;}

<YYINITIAL> {START_OF_DOC_COMMENT}                          { yypushstate(INSIDE_DOC_COMMENT); return SpjTypes.DOC_COMMENT; }
<INSIDE_DOC_COMMENT>{WHITE_SPACE}+                          { yybegin(INSIDE_DOC_COMMENT); return TokenType.WHITE_SPACE; }
<INSIDE_DOC_COMMENT> {DOC_COMMENT_TAG_EXAMPLE}              { yybegin(INSIDE_DOC_COMMENT); return SpjTypes.DOC_COMMENT_TAG; }
<INSIDE_DOC_COMMENT> {DOC_COMMENT_TAG}                      { yybegin(INSIDE_DOC_COMMENT_PARAM); return SpjTypes.DOC_COMMENT_TAG; }
<INSIDE_DOC_COMMENT> {DOC_COMMENT_TEXT}+                    { yybegin(INSIDE_DOC_COMMENT); return SpjTypes.DOC_COMMENT_TEXT; }
<INSIDE_DOC_COMMENT> {CRLF}+                                { yybegin(YYINITIAL); return TokenType.WHITE_SPACE;}

<INSIDE_DOC_COMMENT_PARAM> {DOC_COMMENT_TEXT}+                { yybegin(INSIDE_DOC_COMMENT); return SpjTypes.DOC_COMMENT_VALUE; }
<INSIDE_DOC_COMMENT_PARAM> {WHITE_SPACE}+                     { yybegin(INSIDE_DOC_COMMENT_PARAM); return TokenType.WHITE_SPACE;}
<INSIDE_DOC_COMMENT_PARAM> {CRLF}+                          { yybegin(YYINITIAL); return TokenType.WHITE_SPACE;}

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return SpjTypes.COMMENT; }

<YYINITIAL>{IMPORT}                                         { yypushstate(INSIDE_IMPORT); return SpjTypes.IMPORT; }
<INSIDE_IMPORT>{WHITE_SPACE}+                                  { yypushstate(INSIDE_IMPORT); return TokenType.WHITE_SPACE; }
<INSIDE_IMPORT>{VARIABLE}+                                   { yypushstate(INSIDE_IMPORT); return SpjTypes.IMPORT; }
<INSIDE_IMPORT>{CRLF}+                                       { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<YYINITIAL> {STRING}                                        { yybegin(YYINITIAL); return SpjTypes.STRING; }

/*<YYINITIAL> {PROCEDURE}                                     { yybegin(YYINITIAL); return SpjTypes.PROCEDURE; }*/
<YYINITIAL> {SEPARATOR}                                     { yybegin(YYINITIAL); return SpjTypes.SEPARATOR; }
<YYINITIAL> {PERFORM}                                       { yypushstate(CALL_PROCEDURE); return SpjTypes.PERFORM;}
<YYINITIAL> {CALL}                                          { yypushstate(CALL_COMMAND); return SpjTypes.CALL;}

<YYINITIAL> {IF}                                            { yypushstate(CALL_CONDITION); return SpjTypes.IF; }
<YYINITIAL> {THEN}                                          { yybegin(YYINITIAL); return SpjTypes.THEN; }

<YYINITIAL> {ELSE}                                          { yypushstate(AFTER_ELSE); return SpjTypes.ELSE; }

<AFTER_ELSE>{WHITE_SPACE}+                                  { yybegin(AFTER_ELSE); return TokenType.WHITE_SPACE; }
<AFTER_ELSE>{CRLF}                                          { yybegin(AFTER_ELSE); return SpjTypes.CRLF; }
<AFTER_ELSE> {IF}                                           { yypushstate(CALL_CONDITION); return SpjTypes.ELSEIF; }
<AFTER_ELSE> {THEN}                                         { yypopstate(); return SpjTypes.THEN; }
<AFTER_ELSE>.                                               { yypopstate(); yypushback(yytext().length()); return SpjTypes.BAD_CHARACTER; }

<YYINITIAL> {ENDIF}                                         { yybegin(YYINITIAL); return SpjTypes.ENDIF; }

<YYINITIAL> {FOR}                                           { yypushstate(INSIDE_FOR_ARGUMENTS); return SpjTypes.FOR; }
<YYINITIAL> {ENDFOR}                                        { yybegin(YYINITIAL); return SpjTypes.ENDFOR; }

<YYINITIAL> {WHILE}                                         { yypushstate(CALL_CONDITION); return SpjTypes.WHILE; }
<YYINITIAL> {ENDWHILE}                                      { yybegin(YYINITIAL); return SpjTypes.ENDWHILE; }

<YYINITIAL> {CATCH}                                         { yypushstate(CALL_KEYWORD); return SpjTypes.CATCH; }


<YYINITIAL> {TRY}                                           { yybegin(YYINITIAL); return SpjTypes.TRY; }
<YYINITIAL> {EXCEPT}                                        { yybegin(AFTER_EXCEPT); return SpjTypes.EXCEPT; }

<AFTER_EXCEPT>{WHITE_SPACE}+                                { yybegin(AFTER_EXCEPT); return TokenType.WHITE_SPACE; }
<AFTER_EXCEPT>{CRLF}                                        { yypopstate(); return SpjTypes.CRLF; }
<AFTER_EXCEPT>\(                                            { yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
<AFTER_EXCEPT>\)                                            { yypopstate(); return SpjTypes.RPAR;}

<AFTER_EXCEPT> {FINALLY}                                    { yypopstate(); return SpjTypes.FINALLY; }
<YYINITIAL> {FINALLY}                                       { yybegin(YYINITIAL); return SpjTypes.FINALLY; }
<YYINITIAL> {ENDTRY}                                        { yybegin(YYINITIAL); return SpjTypes.ENDTRY; }


<YYINITIAL>,                                                { yybegin(YYINITIAL); return SpjTypes.COMMA; }

<YYINITIAL> {KEYWORD}                                       { yypushstate(CALL_KEYWORD); return SpjTypes.KEYWORD; }
<YYINITIAL> {RETURN}                                        { yypushstate(CALL_KEYWORD); return SpjTypes.RETURN; }

/*<YYINITIAL> {PROCEDURE_CALL}                                     { yybegin(YYINITIAL); return SpjTypes.PROCEDURE_CALL; }*/
<YYINITIAL> ({VARIABLE})+ / \(                             {
                                                            String __str__ = yytext().toString();
                                                            if(
                                                               __str__.equals("trap") ||
                                                               __str__.equals("throw") ||
                                                               __str__.equals("session") ||
                                                               __str__.equals("local") ||
                                                               __str__.equals("section") ||
                                                               __str__.equals("end") ||
                                                               __str__.equals("break") ||
                                                               __str__.equals("commit") ||
                                                               __str__.equals("rollback") ||
                                                               __str__.equals("transaction") ||
                                                               __str__.equals("compute") ||
                                                               __str__.equals("put") ||
                                                               __str__.equals("remove") ||
                                                               __str__.equals("output") ||
                                                               __str__.equals("print") ||
                                                               __str__.equals("sendmail") ||
                                                               __str__.equals("fork") ||
                                                               __str__.equals("last") ||
                                                               __str__.equals("next")
                                                               ){
                                                                   yypushstate(CALL_KEYWORD); return SpjTypes.KEYWORD;
                                                               }else if(__str__.equals("return")){
                                                                   yypushstate(CALL_KEYWORD); return SpjTypes.RETURN;
                                                               }else if(__str__.equals("for")){
                                                                    yypushstate(INSIDE_FOR_ARGUMENTS); return SpjTypes.FOR;
                                                               }else if(__str__.equals("endfor")){
                                                                    yypushstate(CALL_KEYWORD); return SpjTypes.ENDFOR;
                                                               }else if(
                                                               __str__.equals("if")
                                                               ){
                                                                yypushstate(CALL_CONDITION); return SpjTypes.IF;
                                                               }else if(__str__.equals("while")){
                                                                yypushstate(CALL_CONDITION); return SpjTypes.WHILE;
                                                               }else if(__str__.equals("catch")){
                                                                 yypushstate(CALL_KEYWORD); return SpjTypes.CATCH;
                                                               }else if(__str__.equals("except")){
                                                                 yypushstate(AFTER_EXCEPT); return SpjTypes.EXCEPT;
                                                                }else{
                                                                yypushstate(CALL_COMMAND);
                                                                return SpjTypes.FUNCTION;
                                                               }

                                                           }
<YYINITIAL> {OPER}+                                    {yybegin(YYINITIAL); return SpjTypes.OPER;}
<YYINITIAL> {COMPUTER}+                                    {yybegin(YYINITIAL); return SpjTypes.OPER;}

<YYINITIAL> {NUMBER}          {
                                                            yybegin(YYINITIAL);
                                                            return SpjTypes.NUMBER;
                                                           }

<YYINITIAL> {VARIABLE}+          {
                                                            yybegin(YYINITIAL);
                                                            return SpjTypes.VARIABLE;
                                                           }


<YYINITIAL>{CRLF}                                              { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
<YYINITIAL>.                                                         {return TokenType.ERROR_ELEMENT;}

<WAITING_VALUE> {PROCEDURE}                                     { yybegin(WAITING_VALUE); return SpjTypes.PROCEDURE; }

<DEF_PROCEDURE>{WHITE_SPACE}+                                     { yybegin(DEF_PROCEDURE); return TokenType.WHITE_SPACE; }
<DEF_PROCEDURE>[^(),\"\[\]]+                                      { yybegin(DEF_PROCEDURE); return SpjTypes.PROCEDURE;}
<DEF_PROCEDURE>\(                                                 { yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
<DEF_PROCEDURE>\]                                                 { yypopstate(); return SpjTypes.RBLO;}
<DEF_PROCEDURE>\)                                                 { yybegin(DEF_PROCEDURE); return SpjTypes.RPAR;}
<DEF_PROCEDURE>.                                                         {return TokenType.ERROR_ELEMENT;}


// プロシージャ呼び出し
<CALL_PROCEDURE>{WHITE_SPACE}+                                      { yybegin(CALL_PROCEDURE); return TokenType.WHITE_SPACE; }
<CALL_PROCEDURE>{PROCEDURE_CALL}                                    { yybegin(CALL_PROCEDURE); return SpjTypes.PROCEDURE_CALL; }
<CALL_PROCEDURE>\(                                                  { yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
<CALL_PROCEDURE>\)                                                  { yypopstate(); return SpjTypes.RPAR;}
<CALL_PROCEDURE>.                                                   { return TokenType.ERROR_ELEMENT;}
<CALL_PROCEDURE>{CRLF}                                              {yybegin(CALL_PROCEDURE);return TokenType.WHITE_SPACE;}

<CALL_COMMAND>\(                                                  { yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
<CALL_COMMAND>{WHITE_SPACE}+                                      { yybegin(CALL_COMMAND); return TokenType.WHITE_SPACE; }
<CALL_COMMAND>{PROCEDURE_CALL}                                    { yybegin(CALL_COMMAND); return SpjTypes.COMMAND_CALL; }
<CALL_COMMAND>{CRLF}                                              { yybegin(CALL_COMMAND);return TokenType.WHITE_SPACE;}
<CALL_COMMAND>\)                                                  { yypopstate(); return SpjTypes.RPAR;}
<CALL_COMMAND>.                                                   { return TokenType.ERROR_ELEMENT;}

<CALL_KEYWORD>{WHITE_SPACE}+                                      { yybegin(CALL_KEYWORD); return TokenType.WHITE_SPACE; }
<CALL_KEYWORD>\(                                                  {yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
<CALL_KEYWORD>\)                                                  { yypopstate(); return SpjTypes.RPAR;}
<CALL_KEYWORD>{CRLF}                                              {yypopstate();return TokenType.WHITE_SPACE;}
<CALL_KEYWORD>.                                                   {return TokenType.ERROR_ELEMENT;}

<INSIDE_ARGUMENTS>{END_OF_LINE_COMMENT}                             {yybegin(INSIDE_ARGUMENTS); return SpjTypes.COMMENT; }
<INSIDE_ARGUMENTS>\(                                                { yypushback(1);yypushstate(INSIDE_NESTED_ARGUMENTS); /*return SpjTypes.LPAR;*/}
<INSIDE_ARGUMENTS>{WHITE_SPACE}+                                    {yybegin(INSIDE_ARGUMENTS); return TokenType.WHITE_SPACE; }
<INSIDE_ARGUMENTS>{OPER}+                                           {yybegin(INSIDE_ARGUMENTS); return SpjTypes.OPER;}
<INSIDE_ARGUMENTS>{ORAND}+                                          {yybegin(INSIDE_ARGUMENTS); return SpjTypes.ORAND;}
<INSIDE_ARGUMENTS>{COMPUTER}+                                          {yybegin(INSIDE_ARGUMENTS); return SpjTypes.OPER;}
<INSIDE_ARGUMENTS>{CRLF}                                            {yybegin(INSIDE_ARGUMENTS); return TokenType.WHITE_SPACE; }
<INSIDE_ARGUMENTS> {STRING}                                         {yybegin(INSIDE_ARGUMENTS); return SpjTypes.STRING; }
<INSIDE_ARGUMENTS>,                                                 {yybegin(INSIDE_ARGUMENTS); return SpjTypes.COMMA;}
<INSIDE_ARGUMENTS>{VARIABLE}+ / \(                                  {yypushstate(CALL_COMMAND); return SpjTypes.FUNCTION;}
<INSIDE_ARGUMENTS>{COMPUTER}+                                       {yybegin(INSIDE_ARGUMENTS); return SpjTypes.OPER;}
<INSIDE_ARGUMENTS>{NUMBER}+                                         {yybegin(INSIDE_ARGUMENTS); return SpjTypes.NUMBER;}
<INSIDE_ARGUMENTS>{VARIABLE}+                                       {yybegin(INSIDE_ARGUMENTS); return SpjTypes.ARGUMENT;}
<INSIDE_ARGUMENTS>\)                                                {yypushback(1); yypopstate(); /*return SpjTypes.RPAR;*/}

<INSIDE_ARGUMENTS>.                                                 {return TokenType.ERROR_ELEMENT;}


<INSIDE_NESTED_ARGUMENTS>\(                                                  { yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
<INSIDE_NESTED_ARGUMENTS>{WHITE_SPACE}+                                      { yybegin(INSIDE_NESTED_ARGUMENTS); return TokenType.WHITE_SPACE; }
<INSIDE_NESTED_ARGUMENTS>{CRLF}                                              { yybegin(INSIDE_NESTED_ARGUMENTS);return TokenType.WHITE_SPACE;}
<INSIDE_NESTED_ARGUMENTS>\)                                                  { yypopstate(); return SpjTypes.RPAR;}
<INSIDE_NESTED_ARGUMENTS>.                                                   { return TokenType.ERROR_ELEMENT;}

// ifとwhileのなか


<CALL_CONDITION>{WHITE_SPACE}+                                      { yybegin(CALL_CONDITION); return TokenType.WHITE_SPACE; }
//<CALL_CONDITION>{PROCEDURE_CALL}                                    { yybegin(CALL_CONDITION); return SpjTypes.COMMAND_CALL; }
<CALL_CONDITION>{CRLF}                                              { yybegin(CALL_CONDITION);return TokenType.WHITE_SPACE;}
<CALL_CONDITION>\(                                                  { yypushstate(INSIDE_CONDITION); return SpjTypes.LPARC;}
<CALL_CONDITION>\)                                                  { yypopstate(); /*return SpjTypes.RPARC;*/}
<CALL_CONDITION>.                                                   { yypushback(1);yypopstate();/*return TokenType.ERROR_ELEMENT;*/}

<INSIDE_CONDITION>{END_OF_LINE_COMMENT}                             {yybegin(INSIDE_CONDITION); return SpjTypes.COMMENT; }
<INSIDE_CONDITION>{THEN}                                            {yybegin(INSIDE_CONDITION); return SpjTypes.THEN;}
<INSIDE_CONDITION>\(                                                {yypushstate(INSIDE_CONDITION); return SpjTypes.LPARC;}
<INSIDE_CONDITION>{WHITE_SPACE}+                                    {yybegin(INSIDE_CONDITION); return TokenType.WHITE_SPACE; }
<INSIDE_CONDITION>{OPER}+                                           {yybegin(INSIDE_CONDITION); return SpjTypes.OPER;}
<INSIDE_CONDITION>{ORAND}+                                          {yybegin(INSIDE_CONDITION); return SpjTypes.ORAND;}
<INSIDE_CONDITION>{CRLF}                                            {yybegin(INSIDE_CONDITION); return TokenType.WHITE_SPACE; }
<INSIDE_CONDITION> {STRING}                                         {yybegin(INSIDE_CONDITION); return SpjTypes.STRING; }
<INSIDE_CONDITION>{VARIABLE}+ / \(                                  {yypushstate(CALL_COMMAND); return SpjTypes.FUNCTION;}
<INSIDE_CONDITION>{COMPUTER}+                                       {yybegin(INSIDE_CONDITION); return SpjTypes.OPER;}
<INSIDE_CONDITION>{NUMBER}                                          {yybegin(INSIDE_CONDITION); return SpjTypes.NUMBER;}
<INSIDE_CONDITION>{VARIABLE}+                                       {yybegin(INSIDE_CONDITION); return SpjTypes.VARIABLE;}
<INSIDE_CONDITION>\)                                                {yypopstate(); return SpjTypes.RPARC;}
<INSIDE_CONDITION>.                                                         {return TokenType.ERROR_ELEMENT;}

// forの中
<INSIDE_FOR_ARGUMENTS>{END_OF_LINE_COMMENT}                             {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.COMMENT; }
<INSIDE_FOR_ARGUMENTS>{WHITE_SPACE}+                                    {yybegin(INSIDE_FOR_ARGUMENTS); return TokenType.WHITE_SPACE; }
<INSIDE_FOR_ARGUMENTS>{CRLF}                                            {yybegin(INSIDE_FOR_ARGUMENTS); return TokenType.WHITE_SPACE; }
<INSIDE_FOR_ARGUMENTS>\(                                                {yypushstate(INSIDE_ARGUMENTS); return SpjTypes.LPAR;}
//<INSIDE_FOR_ARGUMENTS>\(                                                {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.LPAR;}
//<INSIDE_FOR_ARGUMENTS>{END_OF_LINE_COMMENT}                             {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.COMMENT; }
//<INSIDE_FOR_ARGUMENTS>{WHITE_SPACE}+                                    {yybegin(INSIDE_FOR_ARGUMENTS); return TokenType.WHITE_SPACE; }
//<INSIDE_FOR_ARGUMENTS>{CRLF}                                            {yybegin(INSIDE_FOR_ARGUMENTS); return TokenType.WHITE_SPACE; }
//<INSIDE_FOR_ARGUMENTS>{STRING}                                          {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.STRING; }
//<INSIDE_FOR_ARGUMENTS>,                                                 {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.COMMA;}
//<INSIDE_FOR_ARGUMENTS>{VARIABLE}+ / \(                                  {yypushstate(CALL_COMMAND); return SpjTypes.FUNCTION;}
//<INSIDE_FOR_ARGUMENTS>{COMPUTER}+                                       {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.OPER;}
//<INSIDE_FOR_ARGUMENTS>{VARIABLE}+                                       {yybegin(INSIDE_FOR_ARGUMENTS); return SpjTypes.ARGUMENT;}
<INSIDE_FOR_ARGUMENTS>\)                                                {yypopstate(); return SpjTypes.RPAR;}
<INSIDE_FOR_ARGUMENTS>.                                                         {return TokenType.ERROR_ELEMENT;}

{NORMAL_CHARACTER}                                                           { return TokenType.ERROR_ELEMENT; }

{CRLF}                                                     { return TokenType.WHITE_SPACE; }

.                                                           {return TokenType.ERROR_ELEMENT;}
