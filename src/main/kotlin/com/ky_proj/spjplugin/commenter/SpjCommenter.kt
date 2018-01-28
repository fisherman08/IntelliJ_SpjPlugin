package com.ky_proj.spjplugin.commenter

/**
 * Created on 2018/01/28.
 */
import com.intellij.lang.Commenter

class SpjCommenter : Commenter {
    override fun getLineCommentPrefix(): String? {
        return "# "
    }

    override fun getBlockCommentPrefix(): String? {
        return null
    }

    override fun getBlockCommentSuffix(): String? {
        return null
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }
}