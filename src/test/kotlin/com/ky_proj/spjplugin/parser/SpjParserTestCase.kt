package com.ky_proj.spjplugin.parser

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.SpjTestCase
import com.ky_proj.spjplugin.util.SpjTreeUtil
import org.junit.Before

abstract class SpjParserTestCase :SpjTestCase(){

    protected var baseFile :PsiFile? = null

    override fun getTestDataPath(): String {
        return "${this.baseDir}/parser"
    }

    protected fun getNodeListOfTypes(filename :String, tokenset :TokenSet) :List<ASTNode>{
        val file : PsiFile? = getPsiFile(filename)
        return getNodeListOfTypes(file, tokenset)
    }

    protected fun getNodeListOfTypes(psiFile: PsiFile?, tokenset :TokenSet) :List<ASTNode>{
        return SpjTreeUtil.findChildByTokenType(psiFile?.originalElement, tokenset)
    }

    protected fun getNodeListOfTypes(element :PsiElement, tokenset :TokenSet) :List<ASTNode>{
        return SpjTreeUtil.findChildByTokenType(element, tokenset)
    }

    protected fun getSizeOfTypesInBaseFile(tokenset :TokenSet) :Int {
        return getNodeListOfTypes(baseFile, tokenset).size
    }

    @Before
    override fun setUp(){
        super.setUp()
        baseFile = getPsiFile("parser_basics.spj")
    }

}
