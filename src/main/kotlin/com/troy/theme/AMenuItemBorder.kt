package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaMenuItemBorder
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import java.awt.Component
import java.awt.Insets

class AMenuItemBorder : DarculaMenuItemBorder() {

    override fun getBorderInsets(c: Component): Insets {
        val borderInsets = super.getBorderInsets(c)
        return JBInsets.addInsets(borderInsets, JBUI.insets(1, 0))
    }

}
