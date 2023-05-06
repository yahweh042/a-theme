package com.troy.theme

import com.intellij.openapi.wm.impl.status.StatusBarUI
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import javax.swing.JComponent

class AStatusBarUI : StatusBarUI() {

    override fun paint(g: Graphics, c: JComponent) {
        val g2d = g.create() as Graphics2D
        val size = c.size
        val r = Rectangle(0, 0, size.width, size.height + ThemeExtConfigState.getInstance().state.listRowHeight)
        try {
            g2d.color = background
            g2d.fill(r)
        } finally {
            g2d.dispose()
        }
    }

    override fun getMinimumSize(c: JComponent): Dimension {
        return super.getMinimumSize(c).also {
            it.height = ThemeExtConfigState.getInstance().state.listRowHeight
        }
    }

    override fun getMaximumSize(c: JComponent): Dimension {
        return super.getMaximumSize(c).also {
            it.height = ThemeExtConfigState.getInstance().state.listRowHeight
        }
    }

}
