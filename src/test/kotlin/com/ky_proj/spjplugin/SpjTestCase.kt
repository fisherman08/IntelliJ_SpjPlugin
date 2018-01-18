package com.ky_proj.spjplugin

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.junit.Ignore
import org.junit.Test

/**
 * Created on 2018/01/18.
 */

abstract class SpjTestCase: LightPlatformCodeInsightFixtureTestCase() {

    protected val baseDir = "src/test/resources/testData"

    override fun getTestDataPath(): String {
        return this.baseDir
    }

    @Ignore
    protected fun getVirtualFile(filename :String) :VirtualFile? {
       return myFixture.copyFileToProject(filename)
    }



}