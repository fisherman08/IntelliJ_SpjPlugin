package com.ky_proj.spjplugin.icon

/**
 * Created on 2018/01/18.
 */

import com.intellij.openapi.util.IconLoader

class SpjIcon {
    companion object {
        val FileName = "/icons/icon_spj.png"
        @JvmStatic val SPJ = IconLoader.getIcon(FileName)

        @JvmStatic val PROCEDURE = IconLoader.getIcon("/icons/procedure.png")
        @JvmStatic val FUNCTION = IconLoader.getIcon("/icons/function.png")
        @JvmStatic val COMMAND = IconLoader.getIcon("/icons/command.png")
    }

}