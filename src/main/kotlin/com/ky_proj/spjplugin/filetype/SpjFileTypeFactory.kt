package com.ky_proj.spjplugin.filetype

/**
 * Created on 2018/01/18.
 */
import com.intellij.openapi.fileTypes.*


class SpjFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
        fileTypeConsumer.consume(SpjFileType.INSTANCE, "spj")
    }
}