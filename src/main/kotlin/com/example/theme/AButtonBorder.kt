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
        val g2 = graphics.create() as Graphics2D

        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    if (MacUIUtil.USE_QUARTZ) RenderingHints.VALUE_STROKE_PURE else RenderingHints.VALUE_STROKE_NORMALIZE)
            val isSmallComboButton = DarculaButtonUI.isSmallVariant(component)
            val diam = HELP_BUTTON_DIAMETER.get()
            val borderWidth = DarculaUIUtil.LW.float
            val bw: Float = 0f
            var arc = if (DarculaButtonUI.isTag(component)) height - bw * 2 - borderWidth * 2 else DarculaUIUtil.BUTTON_ARC.float
            val r = Rectangle(x, y, width, height)
            val paintComboFocus = isSmallComboButton && component.isFocusable && component.hasFocus()
            if (paintComboFocus) {
                g2.color = JBUI.CurrentTheme.Focus.focusColor()
                val border: Path2D = Path2D.Float(Path2D.WIND_EVEN_ODD)
                border.append(RoundRectangle2D.Float(r.x.toFloat(), r.y.toFloat(), r.width.toFloat(), r.height.toFloat(), arc + borderWidth, arc + borderWidth), false)
                border.append(RoundRectangle2D.Float(r.x + borderWidth * 2, r.y + borderWidth * 2, r.width - borderWidth * 4, r.height - borderWidth * 4, arc, arc), false)
                g2.fill(border)
            }
            if (!DarculaButtonUI.isGotItButton(component)) JBInsets.removeFrom(r, JBUI.insets(1))
            g2.translate(r.x, r.y)
            if (!isSmallComboButton) {
                if (component.hasFocus()) {
                    if (UIUtil.isHelpButton(component)) {
                        DarculaUIUtil.paintFocusOval(g2, (r.width - diam) / 2.0f, (r.height - diam) / 2.0f, diam.toFloat(), diam.toFloat())
                    } else if (DarculaButtonUI.isTag(component)) {
                        DarculaUIUtil.paintTag(g2, r.width.toFloat(), r.height.toFloat(), component.hasFocus(), DarculaUIUtil.computeOutlineFor(component))
                    } else {
                        val type = if (DarculaButtonUI.isDefaultButton(component as JComponent)) Outline.defaultButton else Outline.focus
                        DarculaUIUtil.paintOutlineBorder(g2, r.width, r.height, arc, true, true, type)
                    }
                } else if (DarculaButtonUI.isTag(component)) {
                    DarculaUIUtil.paintTag(g2, r.width.toFloat(), r.height.toFloat(), component.hasFocus(), DarculaUIUtil.computeOutlineFor(component))
                }
            }
            g2.paint = getBorderPaint(component)
            if (UIUtil.isHelpButton(component)) {
                g2.draw(Ellipse2D.Float((r.width - diam) / 2.0f, (r.height - diam) / 2.0f, diam.toFloat(), diam.toFloat()))
            } else if (!paintComboFocus) {
                val border: Path2D = Path2D.Float(Path2D.WIND_EVEN_ODD)
                border.append(RoundRectangle2D.Float(0f, 0f, r.width.toFloat(), r.height.toFloat(), arc, arc), false)
                arc = if (arc > borderWidth) arc - borderWidth else 0.0f
                border.append(RoundRectangle2D.Float(borderWidth, borderWidth, r.width - borderWidth * 2, r.height - borderWidth * 2, arc, arc), false)
                g2.fill(border)
            }
        } finally {
            g2.dispose()
        }
    }

    companion object {
        private val HELP_BUTTON_DIAMETER = JBValue.Float(22f)
    }

}
