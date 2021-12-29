package com.ky_proj.spjplugin

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Ignore

/**
 * Created on 2018/01/18.
 */

abstract class SpjTestCase: BasePlatformTestCase() {

    protected val baseDir = "src/test/resources/testData"

    abstract override fun getTestDataPath(): String

    @Ignore
    protected fun getVirtualFile(filename :String) :VirtualFile {
       return myFixture.copyFileToProject(filename)
    }

    @Ignore
    protected fun getPsiFile(filename: String) :PsiFile? {
        return PsiManager.getInstance(project).findFile(getVirtualFile(filename))
    }

}
