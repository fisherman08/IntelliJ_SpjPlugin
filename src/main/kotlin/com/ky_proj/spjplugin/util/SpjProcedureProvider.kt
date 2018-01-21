package com.ky_proj.spjplugin.util
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import com.ky_proj.spjplugin.filetype.SpjFileType
import com.ky_proj.spjplugin.psi.SpjFile
import com.ky_proj.spjplugin.psi.SpjProcedureBlock
import com.ky_proj.spjplugin.psi.SpjProcedureDef
import com.ky_proj.spjplugin.psi.SpjTypes
import java.util.*


/**
 * Created on 2018/01/21.
 */

object SpjProcedureProvider {
    private val systemProcedures :Array<String> = arrayOf(
            "reference",
            "database",
            "init_app",
            "init_session",
            "init_service",
            "init_page",
            "final_page",
            "final_session",
            "action"
    )

    fun listInProject(project : Project, is_only_return : Boolean) :ArrayList<SpjProcedureDef>{
        val definitions = ArrayList<SpjProcedureDef>()
        val virtualFiles = FileTypeIndex.getFiles(SpjFileType.INSTANCE, GlobalSearchScope.allScope(project))

        for (virtualFile in virtualFiles) {
            // ページごとに取得する
            val file = PsiManager.getInstance(project).findFile(virtualFile) as SpjFile? ?: continue
            definitions.addAll(listInFile(file, is_only_return))
        }
        return definitions
    }

    fun listInFile(file: SpjFile, is_only_return : Boolean) :ArrayList<SpjProcedureDef>{
        val definitions = ArrayList<SpjProcedureDef>()
        val namesToBeIgnored = systemProcedures

        // ページをプロシージャブロックごとに分割
        val procs = PsiTreeUtil.getChildrenOfType(file, SpjProcedureBlock::class.java) ?: return definitions

        // プロシージャの定義部分を探す
        for (proc in procs) {
            // return つきのものだけの指定の時にreturnがなかったら無視
            if (is_only_return && SpjTreeUtil.findChildByTokenType(proc, TokenSet.create(SpjTypes.RETURN)).isEmpty()) continue

            val elem = PsiTreeUtil.findChildOfType(proc, SpjProcedureDef::class.java) ?:continue
            val procedureName = elem.firstChild?.nextSibling?.text ?: ""

            if (elem.node.elementType === SpjTypes.PROCEDURE_DEF && !namesToBeIgnored.contains(procedureName)) {
                Collections.addAll(definitions, elem)
            }
        }

        return definitions
    }
}