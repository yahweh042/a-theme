package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaCheckBoxBorder
import com.intellij.util.ui.JBInsets
import java.awt.Component
import java.awt.Insets

class ACheckBoxBorder : DarculaCheckBoxBorder() {

    override fun getBorderInsets(c: Component): Insets = JBInsets.emptyInsets().asUIResource()

}
