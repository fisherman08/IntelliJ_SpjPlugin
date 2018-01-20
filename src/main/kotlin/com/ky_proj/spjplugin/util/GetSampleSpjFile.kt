package com.ky_proj.spjplugin.util

import java.io.IOException
import java.net.URL

/**
 * Created by Y.Kaneko on 2017/03/23.
 */
class GetSampleSpjFile {

    val sample: String
        @Throws(IOException::class)
        get() {
            val url :URL? = this.javaClass.getResource("/sample/sample.spj")
            return  url?.readText() ?: ""
        }

    companion object {
        internal val instance = GetSampleSpjFile()
    }
}
