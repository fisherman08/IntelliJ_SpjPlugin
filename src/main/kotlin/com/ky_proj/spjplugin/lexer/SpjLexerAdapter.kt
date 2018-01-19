package com.ky_proj.spjplugin.lexer

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class SpjLexerAdapter : FlexAdapter(SpjLexer(null as Reader?))