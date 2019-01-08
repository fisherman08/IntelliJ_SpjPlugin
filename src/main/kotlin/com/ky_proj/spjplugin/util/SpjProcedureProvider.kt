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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
            "final_app",
            "final_page",
            "final_session",
            "action"
    )

    /**
     * projectを渡すと、プロジェクト内に存在する定義済みプロシージャをとにかく全部返してくれる
     *
     * @args project プロジェクトインスタンス
     * @args is_only_return trueだとreturn句を含むプロシージャのみに限定
     * @return ArrayList<SpjProcedureDef>
     */
    suspend fun listInProject(project : Project, is_only_return : Boolean) :ArrayList<SpjProcedureDef> = coroutineScope{
        val definitions = ArrayList<SpjProcedureDef>()
        val virtualFiles = FileTypeIndex.getFiles(SpjFileType.INSTANCE, GlobalSearchScope.allScope(project))
        launch {
            for (virtualFile in virtualFiles) {
                val file = PsiManager.getInstance(project).findFile(virtualFile) as SpjFile? ?: continue
                // ページごとに取得する
                launch {
                    definitions.addAll(listInFile(file, is_only_return))
                }

            }
        }.join()

        definitions
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

    suspend fun findDefinitionInProject(project :Project, name : String, is_only_return :Boolean) :ArrayList<SpjProcedureDef>{
        return findDefinitionInList(name, listInProject(project, is_only_return))
    }

    fun findDefinitionInList(name: String, list: ArrayList<SpjProcedureDef>) :ArrayList<SpjProcedureDef>{
        val result = ArrayList<SpjProcedureDef>()

        for (def in list) {
            val procedureName = def.node?.findChildByType(SpjTypes.PROCEDURE)?.text ?: ""
            if(name == procedureName){
                result.add(def)
            }
        }

        return result
    }
}