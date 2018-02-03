package com.ky_proj.spjplugin.NeoPrjFile.lexer

/**
 * Created on 2018/02/03.
 */
import com.intellij.lexer.FlexAdapter
import com.ky_proj.spjplugin.NeoPrjFile.lexer.NeoPrjFileLexer

import java.io.Reader

class NeoPrjFileLexerAdapter : FlexAdapter(NeoPrjFileLexer(null as Reader?))