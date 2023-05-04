package com.troy.theme

import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaComboBoxUI
import com.intellij.openapi.util.IconLoader.getDisabledIcon
import com.intellij.ui.ExperimentalUI
import com.intellij.ui.scale.JBUIScale.scale
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import java.awt.*
import java.awt.geom.Line2D
import java.awt.geom.Path2D
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.plaf.basic.BasicArrowButton


class AComboBoxUI : DarculaComboBoxUI() {

    private val myBorderCompensation: Insets = JBUI.insets(DEFAULT_BORDER_COMPENSATION)
    private val myArc = 5f

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
        if (c !is JComponent) return
        val g2 = g.create() as Graphics2D
        val r = Rectangle(x, y, width, height)
        try {
            checkFocus()
            AThemeUtils.setBorderStyle(g2, comboBox.isEnabled, comboBox.hasFocus())
            g2.draw(
                Line2D.Double(
                    r.x.toDouble(),
                    (r.height - 1).toDouble(),
                    (r.x + r.width).toDouble(),
                    (r.height - 1).toDouble()
                )
            )
        } finally {
            g2.dispose()
        }
    }

    override fun paintBorder(c: Component, g2: Graphics2D, bw: Float, r: Rectangle, lw: Float, arc: Float) {

    }

    override fun createArrowButton(): JButton? {
        val bg = comboBox.background
        val fg = comboBox.foreground
        val button: JButton = object : BasicArrowButton(SOUTH, bg, fg, fg, fg) {
            override fun paint(g: Graphics) {
                val g2 = g.create() as Graphics2D
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
                    paintArrow(g2, this)
                } finally {
                    g2.dispose()
                }
            }

            override fun getPreferredSize(): Dimension {
                val i = if (comboBox != null) comboBox.insets else JBUI.insets(3)
                val height =
                    (if (DarculaUIUtil.isCompact(comboBox)) DarculaUIUtil.COMPACT_HEIGHT.get() else DarculaUIUtil.MINIMUM_HEIGHT.get()) + i.top + i.bottom
                return Dimension(DarculaUIUtil.ARROW_BUTTON_WIDTH.get() + i.left, height)
            }
        }
        button.border = JBUI.Borders.empty()
        button.isOpaque = false
        return button
    }

    override fun paintArrow(g2: Graphics2D, btn: JButton) {
        // if (ExperimentalUI.isNewUI()) {
        //     val icon =
        //         if (comboBox.isEnabled) AllIcons.General.ChevronDown else getDisabledIcon(AllIcons.General.ChevronDown)
        //     val r = getArrowButtonRect(btn)
        //     icon.paintIcon(btn, g2, r.x + (r.width - icon.iconWidth) / 2, r.y + (r.height - icon.iconHeight) / 2)
        // } else {
            g2.color = JBUI.CurrentTheme.Arrow.foregroundColor(comboBox.isEnabled)
            g2.fill(getArrowShape(btn))
        // }
    }

    private fun getArrowButtonRect(button: JButton): Rectangle {
        val result = Rectangle(button.size)
        JBInsets.removeFrom(result, JBUI.insets(1, 0, 1, 1))
        val bw = DarculaUIUtil.BW.get()
        JBInsets.removeFrom(result, JBUI.insets(bw, 0, bw, bw))
        return result
    }

    private fun getArrowShape(button: Component): Shape {
        val r = Rectangle(button.size)
        JBInsets.removeFrom(r, JBUI.insets(1, 0, 1, 1))
        val tW = scale(9)
        val tH = scale(5)
        val xU = (r.width - tW) / 2 - scale(1)
        val yU = (r.height - tH) / 2 + scale(1)
        val path: Path2D = Path2D.Float()
        path.moveTo(xU.toDouble(), yU.toDouble())
        path.lineTo((xU + tW).toDouble(), yU.toDouble())
        path.lineTo((xU + tW / 2.0f).toDouble(), (yU + tH).toDouble())
        path.lineTo(xU.toDouble(), yU.toDouble())
        path.closePath()
        return path
    }


    override fun paint(g: Graphics, c: JComponent) {
        checkFocus()
        AThemeUtils.setHandCursor(c)
        val parent = c.parent
        if (parent != null && c.isOpaque) {
            g.color = if (DarculaUIUtil.isTableCellEditor(c) && editor != null) editor.background else parent.background
            g.fillRect(0, 0, c.width, c.height)
        }
        val r = Rectangle(c.size)
        JBInsets.removeFrom(r, myBorderCompensation)
        if (!comboBox.isEditable) {
            checkFocus()
            paintCurrentValue(g, rectangleForCurrentValue(), hasFocus)
        }
        currentValuePane.removeAll()
    }

    companion object {

        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(component: JComponent): AComboBoxUI {
            return AComboBoxUI()
        }

    }

}
