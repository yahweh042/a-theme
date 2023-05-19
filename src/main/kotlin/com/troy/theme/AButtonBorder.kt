package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonPainter
import com.intellij.util.ui.JBValue
import java.awt.Component
import java.awt.Graphics

class AButtonBorder : DarculaButtonPainter() {

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
    }

    companion object {
        private val HELP_BUTTON_DIAMETER = JBValue.Float(22f)
    }

}
