package com.troy.theme

import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaComboBoxUI
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.JBColor
import com.intellij.ui.OffsetIcon
import com.intellij.ui.SimpleColoredComponent
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.StartupUiUtil
import java.awt.*
import java.awt.geom.Line2D
import javax.swing.*
import javax.swing.border.Border
import javax.swing.plaf.basic.BasicArrowButton


class AComboBoxUI : DarculaComboBoxUI() {

    private val myBorderCompensation: Insets = JBUI.insets(DEFAULT_BORDER_COMPENSATION)

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
        val chevronDown = AllIcons.General.ChevronDown
        val icon = if (comboBox.isEnabled) chevronDown else IconLoader.getDisabledIcon(chevronDown)
        val r = getArrowButtonRect(btn)
        icon.paintIcon(btn, g2, r.x + (r.width - icon.iconWidth) / 2, r.y + (r.height - icon.iconHeight) / 2)
    }

    private fun getArrowButtonRect(button: JButton): Rectangle {
        val result = Rectangle(button.size)
        JBInsets.removeFrom(result, JBUI.insets(1, 0, 1, 1))
        val bw = DarculaUIUtil.BW.get()
        JBInsets.removeFrom(result, JBUI.insets(bw, 0, bw, bw))
        return result
    }

    override fun paint(g: Graphics, c: JComponent) {
        checkFocus()
        AThemeUtils.setHandCursor(c)
        val parent = c.parent
        if (parent != null) {
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

    override fun paintCurrentValueBackground(g: Graphics?, bounds: Rectangle?, hasFocus: Boolean) {
        // super.paintCurrentValueBackground(g, bounds, hasFocus)
    }

    override fun paintCurrentValue(g: Graphics, bounds: Rectangle, hasFocus: Boolean) {
        val renderer = comboBox.renderer
        val value = comboBox.selectedItem
        val c = renderer.getListCellRendererComponent(listBox, value, -1, false, false)

        c.font = comboBox.font
        // c.background = getBackgroundColor()

        if (java.lang.Boolean.TRUE != comboBox.getClientProperty(DISABLE_SETTING_FOREGROUND)) {
            if (hasFocus && !isPopupVisible(comboBox)) {
                c.foreground = listBox.foreground
            } else {
                c.foreground =
                    if (comboBox.isEnabled) comboBox.foreground else JBColor.namedColor(
                        "ComboBox.disabledForeground",
                        comboBox.foreground
                    )
            }
        }

        // paint selection in table-cell-editor mode correctly

        // paint selection in table-cell-editor mode correctly
        val changeOpaque = c is JComponent && DarculaUIUtil.isTableCellEditor(comboBox) && c.isOpaque()
        if (changeOpaque) {
            (c as JComponent).isOpaque = false
        }

        var shouldValidate = false
        if (c is JPanel) {
            shouldValidate = true
        }

        val r = Rectangle(bounds)

        var icon: Icon? = null
        var iPad: Insets? = null
        var border: Border? = null
        var enabled = true
        if (c is SimpleColoredComponent) {
            iPad = c.ipad
            border = c.border
            enabled = c.isEnabled
            c.border = JBUI.Borders.empty()
            c.ipad = JBInsets(0, 0, 0, 0)
            c.isEnabled = comboBox.isEnabled
            icon = c.icon
            if (!c.isIconOnTheRight) {
                c.icon = OffsetIcon.getOriginalIcon(icon)
            }
        } else if (c is JLabel) {
            border = c.border
            c.border = JBUI.Borders.empty()
            icon = c.icon
            c.icon = OffsetIcon.getOriginalIcon(icon)

            // the following trimMiddle approach is not good for smooth resizing:
            // the text jumps as more or less space becomes available.
            // a proper text layout algorithm on painting in DarculaLabelUI can fix that.
            val text = c.text
            val maxWidth = bounds.width - if (padding == null || StartupUiUtil.isUnderDarcula()) 0 else padding.right
            if (StringUtil.isNotEmpty(text) && c.preferredSize.width > maxWidth) {
                val max0 = ObjectUtils.binarySearch(7, text.length - 1) { idx: Int ->
                    c.text = StringUtil.trimMiddle(text, idx)
                    c.preferredSize.width.compareTo(maxWidth)
                }
                val max = if (max0 < 0) -max0 - 2 else max0
                if (max > 7 && max < text.length) {
                    c.text = StringUtil.trimMiddle(text, max)
                }
            }
        } else if (c is JComponent) {
            border = c.border
            c.border = JBUI.Borders.empty()
        }

        currentValuePane.paintComponent(g, c, comboBox, r.x, r.y, r.width, r.height, shouldValidate)

        if (changeOpaque) {
            (c as JComponent).isOpaque = true
        }

        when (c) {
            is SimpleColoredComponent -> {
                c.ipad = iPad!!
                c.icon = icon
                c.border = border
                c.isEnabled = enabled
            }

            is JLabel -> {
                c.border = border
                c.icon = icon
            }

            is JComponent -> {
                c.border = border
            }
        }
    }


    companion object {

        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(component: JComponent): AComboBoxUI {
            return AComboBoxUI()
        }

    }

}
