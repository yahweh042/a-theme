package com.troy.theme

import com.intellij.openapi.actionSystem.impl.IdeaActionButtonLook
import java.awt.*
import java.awt.geom.RoundRectangle2D

class AActionButtonLook : IdeaActionButtonLook() {

    override fun paintLookBackground(g: Graphics, rect: Rectangle, color: Color) {
        val g2 = g.create() as Graphics2D
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
            g2.color = color
            val insets = 1f
            val arc = rect.height + insets * 2
            g2.fill(
                RoundRectangle2D.Float(
                    rect.x - insets,
                    rect.y - insets,
                    rect.width + insets * 2,
                    rect.height + insets * 2,
                    arc,
                    arc
                )
            )
        } finally {
            g2.dispose()
        }
    }

    override fun paintLookBorder(g: Graphics, rect: Rectangle, color: Color) {

    }


}
