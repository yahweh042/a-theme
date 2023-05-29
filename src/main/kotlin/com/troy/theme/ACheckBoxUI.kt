package com.troy.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaCheckBoxUI
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.AbstractButton
import javax.swing.JComponent

class ACheckBoxUI : DarculaCheckBoxUI() {

    override fun installDefaults(b: AbstractButton) {
        super.installDefaults(b)
        AThemeUtils.setHandCursor(b)
    }

    override fun drawCheckIcon(
        c: JComponent,
        g: Graphics2D,
        b: AbstractButton,
        iconRect: Rectangle,
        selected: Boolean,
        enabled: Boolean
    ) {
        val g2 = g.create() as Graphics2D
        try {
            val iconName = if (isIndeterminate(b)) "checkBoxIndeterminate" else "checkBox"
            val op = DarculaUIUtil.getOutline(b)
            val hasFocus = op == null && b.hasFocus()
            val icon = IconLookup.findIcon(iconName, selected || isIndeterminate(b), hasFocus, b.isEnabled)
            icon.paintIcon(b, g2, iconRect.x, iconRect.y)
        } finally {
            g2.dispose()
        }
    }

    companion object {
        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(c: JComponent): ACheckBoxUI {
            return ACheckBoxUI()
        }
    }
}

