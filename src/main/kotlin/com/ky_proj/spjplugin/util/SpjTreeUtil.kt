package com.ky_proj.spjplugin.util

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.ky_proj.spjplugin.psi.SpjTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil

import java.util.ArrayList

/**
 * Created by kaneko on 2017/04/28.
 */
object SpjTreeUtil {

    /**
     * PsiElementを指定して、ツリーを末端まで辿って指定されたSpjTypes.*のASTNodeのリストを返す
     * @param element エレメント
     * @param tokenset トークンセット
     * @return 対象になるASTNodeのリスト
     */
    fun findChildByTokenType(element: PsiElement?, tokenset: TokenSet): List<ASTNode> {
        val result = ArrayList<ASTNode>()

        if (element == null ){
            return result
        }

        val rootNode = element.node
        val children = rootNode.getChildren(TokenSet.ANY)
        for (child in children) {
            SpjTreeUtil.findToken(result, child, tokenset)
        }

        return result
    }


    fun findChildrenOfAllTypes(element: PsiElement?): List<ASTNode>{
        val result = ArrayList<ASTNode>()

        if (element == null ){
            return result
        }

        findTokenAll(result, element.node)

        return result
    }

    private fun findToken(result: MutableList<ASTNode>, node: ASTNode, tokenset: TokenSet) {

        val type = node.elementType
        // 与えられtypeに該当したらリストに追加、それ以外だったら子孫まで辿っていく

        if (tokenset.contains(type)) {
            result.add(node)
        } else {
            val children = node.getChildren(null)
            for (child in children) {
                SpjTreeUtil.findToken(result, child, tokenset)
            }
        }


    }

    private fun findTokenAll(result: MutableList<ASTNode>, node: ASTNode){
        result.add(node)
        val children = node.getChildren(null)
        for (child in children) {
            SpjTreeUtil.findTokenAll(result, child)
        }
    }


}
