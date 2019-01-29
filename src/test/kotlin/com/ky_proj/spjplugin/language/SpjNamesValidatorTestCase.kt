package com.ky_proj.spjplugin.language

import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.filetype.SpjFileType
import junit.framework.TestCase
import org.junit.Ignore
import org.junit.Test



class SpjNamesValidatorTestCase: SpjTestCase(){

    override fun getTestDataPath(): String {
        return "${this.baseDir}/language"
    }

    @Test
    fun testキーワード(){
        val validator = SpjNamesValidator()
        val project = this.project

        TestCase.assertTrue(validator.isKeyword("perform", project))
        TestCase.assertTrue(validator.isKeyword("if", project))
        TestCase.assertTrue(validator.isKeyword("else", project))
        TestCase.assertTrue(validator.isKeyword("for", project))
        TestCase.assertTrue(validator.isKeyword("endfor", project))
        TestCase.assertTrue(validator.isKeyword("while", project))
        TestCase.assertTrue(validator.isKeyword("endwhile", project))
        TestCase.assertTrue(validator.isKeyword("section", project))
        TestCase.assertTrue(validator.isKeyword("local", project))
        TestCase.assertTrue(validator.isKeyword("session", project))
    }

    @Test
    fun test禁止文字列を判定できる(){
        val validator = SpjNamesValidator()
        val project = this.project
        assertTrue(validator.isIdentifier("func:array", project))

        assertFalse(validator.isIdentifier("func=array", project))
        assertFalse(validator.isIdentifier("func;array", project))
        assertFalse(validator.isIdentifier("func/array", project))
        assertFalse(validator.isIdentifier("func*array", project))
        assertFalse(validator.isIdentifier("func+array", project))
        assertFalse(validator.isIdentifier("func-array", project))
        assertFalse(validator.isIdentifier("func%array", project))

    }

    @Test
    fun test数値から始まるものはダメ(){
        val validator = SpjNamesValidator()
        val project = this.project

        assertFalse(validator.isIdentifier("1hoge", project))
    }

    @Test
    fun test空文字はダメ(){
        val validator = SpjNamesValidator()
        val project = this.project
        assertFalse(validator.isIdentifier("", project))
    }

    @Test
    fun testNeoのキーワードはダメ(){
        val validator = SpjNamesValidator()
        val project = this.project
        assertFalse(validator.isIdentifier("perform", project))
    }
}