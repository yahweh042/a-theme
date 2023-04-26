package com.example.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaComboBoxUI
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import java.awt.*
import java.awt.geom.Line2D
import java.awt.geom.Path2D
import java.awt.geom.Rectangle2D
import javax.swing.JComponent


class AComboBoxUI : DarculaComboBoxUI() {

    private val myBorderCompensation: Insets = JBUI.insets(DEFAULT_BORDER_COMPENSATION)
    private val myArc = 5f

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
        if (c !is JComponent) return
        val g2 = g.create() as Graphics2D
        val bw = DarculaUIUtil.BW.float
        val r = Rectangle(x, y, width, height)
        try {
            checkFocus()
            if (!DarculaUIUtil.isTableCellEditor(c)) {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
                JBInsets.removeFrom(r, myBorderCompensation)
                g2.translate(r.x, r.y)
                val lw = DarculaUIUtil.LW.float
                val op = DarculaUIUtil.getOutline(comboBox)
                if (op == null) {

                } else {
                    val border = Path2D.Float(0)
                    if (comboBox.isEnabled) {
                        border.append(Rectangle2D.Float(r.x.toFloat(), r.height - bw, r.width.toFloat(), bw), false)
                        g2.fill(border)
                    }
                }

                if (comboBox.isEnabled && op != null) {
                    DarculaUIUtil.paintOutlineBorder(g2, r.width, r.height, myArc, true, hasFocus, op)
                } else {
                    paintBorder(c, g2, if (DarculaUIUtil.isBorderless(c)) lw else bw, r, lw, myArc)
                }
            } else {
                DarculaUIUtil.paintCellEditorBorder(g2, c, r, hasFocus)
            }
        } finally {
            g2.dispose()
        }
    }

    override fun paintBorder(c: Component, g2: Graphics2D, bw: Float, r: Rectangle, lw: Float, arc: Float) {
        if (!comboBox.isEnabled) {
            g2.stroke =
                BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0.0f, floatArrayOf(1.0f, 2.0f), 0.0f)
        }
        if (comboBox.hasFocus()) {
            g2.color = JBColor.BLUE
        }
        g2.draw(
            Line2D.Double(
                r.x.toDouble(),
                (r.height - 1).toDouble(),
                (r.x + r.width).toDouble(),
                (r.height - 1).toDouble()
            )
        )
    }

    override fun paint(g: Graphics, c: JComponent) {
        checkFocus()
        val parent = c.parent
        if (parent != null && c.isOpaque) {
            g.color = if (DarculaUIUtil.isTableCellEditor(c) && editor != null) editor.background else parent.background
            g.fillRect(0, 0, c.width, c.height)
        }

        val g2 = g.create() as Graphics2D
        val r = Rectangle(c.size)
        JBInsets.removeFrom(r, myBorderCompensation)

        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
            g2.translate(r.x, r.y)
            val bw = if (DarculaUIUtil.isBorderless(c)) DarculaUIUtil.LW.float else DarculaUIUtil.BW.float
            g2.color = getBackgroundColor()
            // g2.fill(getOuterShape(r, bw, myArc))
        } finally {
            g2.dispose()
        }

        if (!comboBox.isEditable) {
            checkFocus()
            paintCurrentValue(g, rectangleForCurrentValue(), hasFocus)
        }
        currentValuePane.removeAll()
    }

    private fun getBackgroundColor(): Color = JBColor.PanelBackground


    companion object {

        @JvmStatic
        fun createUI(component: JComponent): AComboBoxUI {
            return AComboBoxUI()
        }

    }

}
