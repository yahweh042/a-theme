package com.troy.theme

import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaCheckBoxUI
import com.intellij.openapi.util.IconLoader
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.AbstractButton
import javax.swing.Icon
import javax.swing.JComponent

class ACheckBoxUI : DarculaCheckBoxUI() {

    override fun drawCheckIcon(
        c: JComponent,
        g: Graphics2D,
        b: AbstractButton,
        iconRect: Rectangle,
        selected: Boolean,
        enabled: Boolean
    ) {
        val g2 = g.create() as Graphics2D
        AThemeUtils.setHandCursor(c)
        try {
            val iconName = if (isIndeterminate(b)) "checkBoxIndeterminate" else "checkBox"
            val op = DarculaUIUtil.getOutline(b)
            val hasFocus = op == null && b.hasFocus()
            val icon = findIcon(iconName, selected || isIndeterminate(b), hasFocus, b.isEnabled)
            icon.paintIcon(b, g2, iconRect.x, iconRect.y)
        } finally {
            g2.dispose()
        }
    }

    private fun findIcon(
        name: String,
        selected: Boolean = false,
        focused: Boolean = false,
        enabled: Boolean = true,
        editable: Boolean = false,
        pressed: Boolean = false,
    ): Icon {
        var key = name
        if (editable) {
            key += "Editable"
        }
        if (selected) {
            key += "Selected"
        }
        when {
            pressed -> key += "Pressed"
            focused -> key += "Focused"
            !enabled -> key += "Disabled"
        }
        val path = "icons/${key}.svg"
        return IconLoader.findIcon(path, ACheckBoxUI::class.java.classLoader) ?: AllIcons.Actions.Stub
    }

    companion object {
        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(c: JComponent): ACheckBoxUI {
            return ACheckBoxUI()
        }
    }
}

