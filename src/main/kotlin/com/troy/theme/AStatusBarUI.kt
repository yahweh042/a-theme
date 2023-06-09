package com.troy.theme

import com.intellij.openapi.wm.impl.status.StatusBarUI
import com.intellij.util.ui.JBUI
import java.awt.*
import javax.swing.JComponent
import javax.swing.border.Border

class AStatusBarUI : StatusBarUI() {

    override fun installUI(c: JComponent) {
        super.installUI(c)
        c.border = object : Border {
            override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
                g.color = background
                g.fillRect(x, y, width, height)
            }

            override fun getBorderInsets(c: Component?): Insets {
                return JBUI.insets(4, 2)
            }

            override fun isBorderOpaque(): Boolean {
                return true
            }

        }
    }

    override fun paint(g: Graphics, c: JComponent) {
        val g2d = g.create() as Graphics2D
        val size = c.size
        val r = Rectangle(size)
        try {
            g2d.color = background
            g2d.fill(r)
        } finally {
            g2d.dispose()
        }
    }

    override fun getMinimumSize(c: JComponent): Dimension {
        return JBUI.size(100, ThemeExtConfigState.getInstance().state.statusBarHeight)
    }

    override fun getMaximumSize(c: JComponent): Dimension {
        return JBUI.size(Int.MAX_VALUE, ThemeExtConfigState.getInstance().state.statusBarHeight)
    }

    companion object {
        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(c: JComponent): AStatusBarUI {
            return AStatusBarUI()
        }
    }

}
