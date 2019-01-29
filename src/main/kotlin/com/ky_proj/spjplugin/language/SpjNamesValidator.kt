package com.ky_proj.spjplugin.language

import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

class SpjNamesValidator :NamesValidator{

    companion object {
        val keywords = arrayOf(
                "perform",
                "if",
                "else",
                "for",
                "endfor",
                "while",
                "endwhile",
                "section",
                "local",
                "session"
        )

        val invalid_characters = arrayOf(";", "+", "-", "*", "/", "=", "%")
    }
    override fun isKeyword(name: String, project: Project): Boolean {
        return keywords.contains(name)
    }

    override fun isIdentifier(name: String, project: Project): Boolean {
        for(c in invalid_characters){
            if (name.contains(c)){
                // 禁止文字列
                return false
            }
        }

        // neoのキーワード
        if(keywords.contains(name)){
            return false
        }

        // 空文字
        if(name.isNullOrEmpty()){
            return false
        }

        // 数値から始まる
        if(name.first().isDigit()){
            return false
        }

        return true
    }
}