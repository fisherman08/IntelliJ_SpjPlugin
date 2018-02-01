package com.ky_proj.spjplugin.setting

import com.intellij.openapi.project.Project

/**
 * Created on 2018/01/27.
 */
// TODO: 根本的に仮実装なう
class SpjSetting(project :Project){

    fun isEnhanceMode() :Boolean{
        return (getNeoVersion() == 4.0f)
    }

    fun getNeoVersion() :Float{
        return 4.0f
    }
}