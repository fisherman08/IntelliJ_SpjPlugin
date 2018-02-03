package com.ky_proj.spjplugin.NeoPrjFile.filetype

/**
 * Created on 2018/02/03.
 */
import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class NeoPrjFileFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
        fileTypeConsumer.consume(NeoPrjFileFileType.INSTANCE, "prj")
    }
}