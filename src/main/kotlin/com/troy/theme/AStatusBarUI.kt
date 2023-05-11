package com.troy.theme

import com.intellij.openapi.wm.impl.status.StatusBarUI
import com.intellij.util.ui.JBUI
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.JComponent

class AStatusBarUI : StatusBarUI() {

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
