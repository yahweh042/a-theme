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
            val arc = rect.height -2f
            g2.fill(
                RoundRectangle2D.Float(
                    rect.x.toFloat() + 1f,
                    rect.y.toFloat() + 1f,
                    rect.width.toFloat() - 2f,
                    rect.height.toFloat() - 2f,
                    arc.toFloat(),
                    arc.toFloat()
                )
            )
        } finally {
            g2.dispose()
        }
    }

    override fun paintLookBorder(g: Graphics, rect: Rectangle, color: Color) {

    }


}
