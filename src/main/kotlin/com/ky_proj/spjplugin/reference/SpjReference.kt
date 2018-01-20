package com.ky_proj.spjplugin.reference

import com.intellij.psi.tree.IElementType
import com.intellij.util.IncorrectOperationException
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.ky_proj.spjplugin.psi.SpjProcedureDef
import com.ky_proj.spjplugin.psi.SpjTypes
import org.jetbrains.annotations.*

import java.util.*

class SpjReference(element: PsiElement) : PsiReferenceBase<PsiElement>(element, TextRange(0, element.text.length)), PsiPolyVariantReference {
    private var myResolveResults: Array<ResolveResult>? = null

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return ArrayList<ResolveResult>().toTypedArray()

       /* if (myResolveResults != null) {
            // すでにキャッシュされてるものがあってかつ全てが有効なららそれを返す。
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
                return myResolveResults
            }
        }

        val project = myElement.project
        val file = myElement.containingFile
        val elementType = myElement.node.elementType

        val results = ArrayList<ResolveResult>()


        var proc: ASTNode? = null

        if (elementType === SpjTypes.CALLING_PROCEDURE ||
                elementType === SpjTypes.PROC_CALL ||
                elementType === SpjTypes.CALLING_FUNCTION) {
            if (elementType === SpjTypes.CALLING_PROCEDURE) {
                // calling_procedureエレメントならプロシージャ名を探す
                val proc_call = myElement.node.findChildByType(SpjTypes.PROC_CALL)
                if (proc_call != null) {
                    proc = proc_call.findChildByType(SpjTypes.PROCEDURE_CALL)
                }

            } else if (elementType === SpjTypes.PROC_CALL) {

                proc = myElement.node.findChildByType(SpjTypes.PROCEDURE_CALL)

            } else if (elementType === SpjTypes.CALLING_FUNCTION) {
                // 関数形式の呼び出しの時
                proc = myElement.node.findChildByType(SpjTypes.FUNCTION)

            }

            if (proc != null) {

                // プロシージャを呼び出してるところだったら定義元にジャンプさせる
                val proc_name = proc.text

                // まずは同じページの中で定義を探す
                var defs = SpjUtil.GET_PROCS.getProcDefsByName(project, file, proc_name)

                // もし同じページになかったらプロジェクト全体から探す
                if (defs.size == 0) {
                    defs = SpjUtil.GET_PROCS.getProcDefsByName(project, proc_name)
                }

                for (def in defs) {
                    results.add(PsiElementResolveResult(def))
                }
                // 何度もgetProcDefsを呼ぶと死ぬのでResolveResult[]をキャッシュしておく。
                // ResolveResultを持っておくと、参照先のプロシージャ定義が移動したりしてもよしなに対応してくれる模様
                if (results.size > 0) {
                    myResolveResults = results.toTypedArray()
                    return myResolveResults
                }

            }

        }

        results.add(PsiElementResolveResult(myElement))
        return results.toTypedArray()
        */
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        val project = myElement.project
        //List<SpjProperty> properties = SpjUtil.findProperties(project);
        //List<LookupElement> variants = new ArrayList<LookupElement>();
        //List<SpjProcedureDef> defs = SpjUtil.GET_PROCS.getProcDefs(project);
        return  ArrayList<Any>().toTypedArray()
    }

    @Throws(IncorrectOperationException::class)
    override fun handleElementRename(newElementName: String): PsiElement {
        /*if (newElementName.endsWith(PropertiesFileType.DOT_DEFAULT_EXTENSION)) {
            newElementName = newElementName.substring(0, newElementName.lastIndexOf(PropertiesFileType.DOT_DEFAULT_EXTENSION));
        }

        final String currentValue = getValue();
        final char packageDelimiter = getPackageDelimiter();
        final int index = currentValue.lastIndexOf(packageDelimiter);
        if (index != -1) {
            newElementName = currentValue.substring(0, index) + packageDelimiter + newElementName;
        }

        return super.handleElementRename(newElementName);*/
        return myElement
    }
}