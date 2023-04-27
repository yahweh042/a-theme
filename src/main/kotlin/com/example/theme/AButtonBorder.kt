package com.example.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil.Outline
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonPainter
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.util.ui.*
import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.Path2D
import java.awt.geom.RoundRectangle2D
import javax.swing.JComponent

class AButtonBorder : DarculaButtonPainter() {

    override fun paintBorder(component: Component, graphics: Graphics, x: Int, y: Int, width: Int, height: Int) {

    }

    companion object {
        private val HELP_BUTTON_DIAMETER = JBValue.Float(22f)
    }

}
