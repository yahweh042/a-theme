package com.troy.theme

import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaComboBoxUI
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.popup.ListSeparator
import com.intellij.openapi.util.ColoredItem
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.GroupedComboBoxRenderer
import com.intellij.ui.JBColor
import com.intellij.ui.OffsetIcon
import com.intellij.ui.SimpleColoredComponent
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.StartupUiUtil
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.geom.Path2D
import javax.swing.*
import javax.swing.border.Border
import javax.swing.plaf.basic.BasicArrowButton


class AComboBoxUI : DarculaComboBoxUI() {

    private val myBorderCompensation: Insets = JBUI.insets(DEFAULT_BORDER_COMPENSATION)
    private val myArc: Int = ThemeExtConfigState.getInstance().state.comboBoxState.arc
    private var myArrowButton: AArrowButton? = null

    override fun installUI(c: JComponent) {
        super.installUI(c)
        AThemeUtils.setHandCursor(c)
        comboBox.renderer = object : GroupedComboBoxRenderer<Any>(comboBox as ComboBox<Any>) {
            override fun separatorFor(value: Any): ListSeparator? {
                TODO("Not yet implemented")
            }

        }
    }

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
        if (c !is JComponent) return
        val g2 = g.create() as Graphics2D
        val r = Rectangle(x, y, width, height)
        try {
            checkFocus()
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
            paintBorder(c, g2, 1.0f, r, 1.0f, myArc.toFloat())
        } finally {
            g2.dispose()
        }
    }

    override fun paintBorder(c: Component, g2: Graphics2D, bw: Float, r: Rectangle, lw: Float, arc: Float) {
        val border: Path2D = Path2D.Float(Path2D.WIND_EVEN_ODD)
        border.append(AThemeUtils.getOuterShape(r, bw, arc), false)
        border.append(AThemeUtils.getInnerShape(r, bw, lw, if ((arc - lw) > 0) arc - lw else 0f), false)
        g2.color = DarculaUIUtil.getOutlineColor(c.isEnabled, hasFocus)
        g2.fill(border)
    }

    // override fun createPopup(): ComboPopup {
    //     return AComboPopup(comboBox)
    // }

    override fun createRenderer(): ListCellRenderer<Any> {
        return object : GroupedComboBoxRenderer<Any>((comboBox as ComboBox<Any>?)!!) {
            override fun separatorFor(value: Any): ListSeparator? {
                return null
            }
        }
    }

    override fun createArrowButton(): JButton {
        val bg = comboBox.background
        val fg = comboBox.foreground
        this.myArrowButton = AArrowButton(bg, fg, fg, fg)
        this.myArrowButton?.border = JBUI.Borders.empty()
        this.myArrowButton?.isOpaque = false
        return this.myArrowButton!!
    }

    inner class AArrowButton(
        background: Color,
        shadow: Color,
        darkShadow: Color,
        highlight: Color
    ) : BasicArrowButton(SOUTH, background, shadow, darkShadow, highlight) {
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

        private fun paintArrow(g2: Graphics2D, btn: JButton) {
            val chevronDown = if (comboBox.isPopupVisible) AllIcons.General.ChevronUp else AllIcons.General.ChevronDown
            val icon = if (comboBox.isEnabled) chevronDown else IconLoader.getDisabledIcon(chevronDown)
            val r = getArrowButtonRect(btn)
            icon.paintIcon(btn, g2, r.x + (r.width - icon.iconWidth) / 2, r.y + (r.height - icon.iconHeight) / 2)
        }

        private fun getArrowButtonRect(button: JButton): Rectangle {
            val result = Rectangle(button.size)
            JBInsets.removeFrom(result, JBUI.insets(1, 0, 1, 1))
            return result
        }

        override fun getPreferredSize(): Dimension {
            val i = if (comboBox != null) comboBox.insets else JBUI.insets(3)
            val height =
                (if (DarculaUIUtil.isCompact(comboBox)) DarculaUIUtil.COMPACT_HEIGHT.get() else DarculaUIUtil.MINIMUM_HEIGHT.get()) + i.top + i.bottom
            return Dimension(DarculaUIUtil.ARROW_BUTTON_WIDTH.get() + i.left, height)
        }
    }

    override fun paint(g: Graphics, c: JComponent) {
        checkFocus()
        val parent = c.parent
        if (parent != null) {
            g.color = parent.background
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

    override fun paintCurrentValue(g: Graphics, bounds: Rectangle, hasFocus: Boolean) {
        val renderer = comboBox.renderer
        val value = comboBox.selectedItem
        val c = renderer.getListCellRendererComponent(listBox, value, -1, false, false)

        c.font = comboBox.font
        c.background = g.color

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
        // val changeOpaque = c is JComponent && DarculaUIUtil.isTableCellEditor(comboBox) && c.isOpaque()
        // if (changeOpaque) {
        //     (c as JComponent).isOpaque = false
        // }

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
            val maxWidth = bounds.width - if (padding == null) 0 else padding.right
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

        // if (changeOpaque) {
        //     (c as JComponent).isOpaque = true
        // }

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

    override fun update(g: Graphics, c: JComponent) {
        super.update(g, c)
        this.myArrowButton?.repaint()
    }

    private fun getBackgroundColor(): Color {
        val bg = comboBox.background
        return if (comboBox.isEditable && editor != null) {
            if (comboBox.isEnabled) editor.background else UIUtil.getComboBoxDisabledBackground()
        } else {
            val value = comboBox.selectedItem
            val coloredItemColor = if (value is ColoredItem) value.color else null
            ObjectUtils.notNull(
                coloredItemColor,
                if (comboBox.isEnabled) JBColor.namedColor(
                    "ComboBox.nonEditableBackground",
                    JBColor.namedColor("ComboBox.darcula.nonEditableBackground", JBColor(0xfcfcfc, 0x3c3f41))
                ) else UIUtil.getComboBoxDisabledBackground()
            )
        }
    }

    companion object {

        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(c: JComponent): AComboBoxUI {
            return AComboBoxUI()
        }

    }

}
