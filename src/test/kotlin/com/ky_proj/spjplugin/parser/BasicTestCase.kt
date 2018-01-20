package com.ky_proj.spjplugin.parser

import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes
import org.junit.Test


class BasicTestCase :SpjParserTestCase(){
    // Basics

    @Test
    fun testArgs(){
        assertEquals(11, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.ARGS)))
    }

    @Test
    fun testArguments(){
        assertEquals(11, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.ARGUMENT)))
    }

    @Test
    fun testCallingCommands(){
        assertEquals(2, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.CALLING_COMMAND)))
    }

    @Test
    fun testCallingFunctions(){
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.CALLING_FUNCTION)))
    }

    @Test
    fun testCallingProcedures(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.CALLING_PROCEDURE)))
    }

    @Test
    fun testParseProcedureBlock(){
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.PROCEDURE_BLOCK)))
    }

    @Test
    fun testParseCommentAndDocComment(){

        assertEquals(2, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.COMMENT)))

        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.DOC_COMMENT_ROW)))
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.DOC_COMMENT)))
        assertEquals(2, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.DOC_COMMENT_TAG)))
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.DOC_COMMENT_VALUE)))
    }


    @Test
    fun testVariables(){
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.VARIABLE)))
    }


    @Test
    fun testNumbers(){
        assertEquals(5, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.NUMBER)))
    }

    @Test
    fun testCommas(){
        assertEquals(7, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.COMMA)))
    }

    @Test
    fun testStrings(){
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.STRING)))
    }

    @Test
    fun testConditions(){
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.CONDITION)))
    }

    @Test
    fun testIfBlock(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.IFBLOCK)))
    }

    @Test
    fun testIf(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.IF)))
    }

    @Test
    fun testElseIf(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.ELSEIF)))
    }

    @Test
    fun testThen(){
        assertEquals(3, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.THEN)))
    }

    @Test
    fun testElse(){
        assertEquals(2, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.ELSE)))
    }

    @Test
    fun testEndIf(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.ENDIF)))
    }

    @Test
    fun testTryBlock(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.TRYBLOCK)))
    }

    @Test
    fun testTry(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.TRY)))
    }

    @Test
    fun testExcept(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.EXCEPT)))
    }

    @Test
    fun testFinally(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.FINALLY)))
    }

    @Test
    fun testEndTry(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.ENDTRY)))
    }

    @Test
    fun testWhileBlock(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.WHILEBLOCK)))
    }

    @Test
    fun testWhile(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.WHILE)))
    }

    @Test
    fun testForBlock(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.FORBLOCK)))
    }

    @Test
    fun testFor(){
        assertEquals(1, getSizeOfTypesInBaseFile(TokenSet.create(SpjTypes.FOR)))
    }
}