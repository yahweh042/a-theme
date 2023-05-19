package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaRadioButtonUI
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.AbstractButton
import javax.swing.JComponent

class ARadioButtonUI : DarculaRadioButtonUI() {

    override fun installDefaults(b: AbstractButton) {
        super.installDefaults(b)
        AThemeUtils.setHandCursor(b)
    }

    override fun paintIcon(c: JComponent, g: Graphics2D?, iconRect: Rectangle) {
        val icon = IconLookup.findIcon("radio", (c as AbstractButton).isSelected, c.hasFocus(), c.isEnabled())
        icon.paintIcon(c, g, iconRect.x, iconRect.y)
    }

    companion object {

        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(c: JComponent): ARadioButtonUI {
            return ARadioButtonUI()
        }

    }

}
