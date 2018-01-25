package com.ky_proj.spjplugin.util

/**
 * Created on 2018/01/19.
 */
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiFile
import com.ky_proj.spjplugin.psi.SpjTypes

import java.util.ArrayList

class GetVariables(file: PsiFile) {
    private var myFile: PsiFile? = file

    val variables: Array<String>
        get() {
            val result = ArrayList<String>()

            val children = myFile!!.children

            children.forEach { child ->
                val node = child.node
                getChildVariables(result, node)
            }

            return result.toTypedArray()
        }



    private fun getChildVariables(result: MutableList<String>, node: ASTNode) {

        val type = node.elementType

        // 変数か引数だったら名前を取る、それ以外だったら子孫まで辿っていく
        if (type === SpjTypes.ARGUMENT || type === SpjTypes.VARIABLE) {
            var variableName = node.text
            if (variableName.substring(0, 1) == "?") {
                // ?から始まるフォーム変数は無視
                return
            }
            if (variableName.substring(0, 1) == "$" && variableName.length > 1) {
                // $から始まってたら一旦$をとる
                variableName = variableName.substring(1, variableName.length)
            }
            result.add(variableName)

        } else {

            val children = node.getChildren(null)
            for (__child in children) {
                getChildVariables(result, __child)
            }
        }
    }
}
