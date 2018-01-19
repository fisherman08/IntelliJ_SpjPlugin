package com.ky_proj.spjplugin

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.junit.Ignore
import org.junit.Test

/**
 * Created on 2018/01/18.
 */

abstract class SpjTestCase: LightPlatformCodeInsightFixtureTestCase() {

    protected val baseDir = "src/test/resources/testData"

    override abstract fun getTestDataPath(): String


    @Ignore
    protected fun getVirtualFile(filename :String) :VirtualFile {
       return myFixture.copyFileToProject(filename)
    }

    @Ignore
    protected fun getPsiFile(filename: String) :PsiFile? {
        return PsiManager.getInstance(project).findFile(getVirtualFile(filename))
    }

}