package com.troy.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaTextBorder
import com.intellij.ide.ui.laf.darcula.ui.TextFieldWithPopupHandlerUI
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBValue
import com.intellij.util.ui.MacUIUtil
import java.awt.*
import java.awt.geom.Line2D
import java.awt.geom.Path2D
import java.awt.geom.Rectangle2D
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.text.JTextComponent

class AFieldBorder : DarculaTextBorder() {

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
        if ((c as JComponent).getClientProperty("JTextField.Search.noBorderRing") == true) {
            return
        }
        val r = Rectangle(x, y, width, height)
        val focused = isFocused(c)
        if (TextFieldWithPopupHandlerUI.isSearchField(c)) {
            paintSearchArea(g as Graphics2D, r, c as JTextComponent, false)
        } else if (DarculaUIUtil.isTableCellEditor(c)) {
            DarculaUIUtil.paintCellEditorBorder(g as Graphics2D, c, r, focused)
        } else if (c.parent !is JComboBox<*>) {
            val g2 = g.create() as Graphics2D
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g2.setRenderingHint(
                    RenderingHints.KEY_STROKE_CONTROL,
                    if (MacUIUtil.USE_QUARTZ) RenderingHints.VALUE_STROKE_PURE else RenderingHints.VALUE_STROKE_NORMALIZE
                )
                JBInsets.removeFrom(r, paddings())
                g2.translate(r.x, r.y)
                clipForBorder(c, g2, r.width, r.height)
                val border = Path2D.Float(0)
                AThemeUtils.setBorderStyle(g2, c.isEnabled, isFocused(c))
                g2.draw(
                    Line2D.Double(
                        r.x.toDouble(),
                        (r.height - 1).toDouble(),
                        (r.x + r.width).toDouble(),
                        (r.height - 1).toDouble()
                    )
                )
                g2.fill(border)
            } finally {
                g2.dispose()
            }
        }
    }

    override fun paintSearchArea(g: Graphics2D, r: Rectangle, c: JTextComponent, fillBackground: Boolean) {
        val g2 = g.create() as Graphics2D
        val bw = 1f
        val border = Path2D.Float(0)
        AThemeUtils.setBorderStyle(g2, c.isEnabled, isFocused(c))
        border.append(Rectangle2D.Float(r.x.toFloat(), r.height - bw, r.width.toFloat(), bw), false)
        g2.fill(border)
    }

}