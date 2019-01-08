package com.ky_proj.spjplugin.reference

import com.intellij.util.IncorrectOperationException
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.ky_proj.spjplugin.psi.SpjFile
import com.ky_proj.spjplugin.psi.SpjTypes
import com.ky_proj.spjplugin.util.SpjProcedureProvider
import kotlinx.coroutines.runBlocking

import java.util.*

class SpjReference(element: PsiElement) : PsiReferenceBase<PsiElement>(element, TextRange(0, element.text.length)), PsiPolyVariantReference {
    private var myResolveResults: Array<ResolveResult>? = null

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {

       if (myResolveResults != null) {
            // すでにキャッシュされてるものがあってかつ全てが有効ならそれを返す。
            var is_cache_valid = true
            for (res in myResolveResults!!) {
                val element = res.element
                if (element == null || !element.isValid) {
                    is_cache_valid = false
                    break
                }
            }

            if (is_cache_valid) {
                // キャッシュが全て有効ならそのまま返す
                return myResolveResults!!
            }
        }

        val elementType   = myElement.node.elementType
        var results       = ArrayList<ResolveResult>()
        var procedureName = "";

        when(elementType) {

            SpjTypes.CALLING_PROCEDURE -> {
                // calling_procedureエレメントならプロシージャ名を探す
                procedureName = myElement.node.findChildByType(SpjTypes.PROC_CALL)?.findChildByType(SpjTypes.PROCEDURE_CALL)?.text ?:""
            }

            SpjTypes.PROC_CALL -> {
                procedureName = myElement.node.findChildByType(SpjTypes.PROCEDURE_CALL)?.text ?:""
            }

            SpjTypes.CALLING_FUNCTION -> {
                procedureName = myElement.node.findChildByType(SpjTypes.FUNCTION)?.text ?: ""
            }

            else -> {
                results.add(PsiElementResolveResult(myElement))
                return results.toTypedArray()
            }

        }

        if(procedureName.isEmpty()){
            results.add(PsiElementResolveResult(myElement))
            return results.toTypedArray()
        }

        // プロシージャ名で定義元を探す
        results = findProcedureDef(procedureName)

        // 何度もgetProcDefsを呼ぶと死ぬのでResolveResult[]をキャッシュしておく。
        // ResolveResultを持っておくと、参照先のプロシージャ定義が移動したりしてもよしなに対応してくれる模様
        if (results.size > 0) {
            myResolveResults = results.toTypedArray()
            return myResolveResults!!
        }

        results.add(PsiElementResolveResult(myElement))
        return results.toTypedArray()
    }


    private fun findProcedureDef(procedureName :String): ArrayList<ResolveResult>{
        val project     = myElement.project
        val file        = myElement.containingFile as SpjFile
        val results    = ArrayList<ResolveResult>()

        runBlocking {
            // まずは同じページの中で定義を探す
            var defs = SpjProcedureProvider.findDefinitionInList(procedureName, SpjProcedureProvider.listInFile(file, false))

            // もし同じページになかったらプロジェクト全体から探す
            if (defs.size == 0) {
                defs = SpjProcedureProvider.findDefinitionInList(procedureName, SpjProcedureProvider.listInProject(project, false))
            }

            for (def in defs) {
                results.add(PsiElementResolveResult(def))
            }
        }

        return results
    }


    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        return  emptyArray()
    }

    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newElementName: String): PsiElement {

        return myElement
    }
}