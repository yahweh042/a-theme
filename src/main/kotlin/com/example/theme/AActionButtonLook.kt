package com.example.theme

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
            g2.fill(
                RoundRectangle2D.Float(
                    rect.x.toFloat(),
                    rect.y.toFloat(),
                    rect.width.toFloat(),
                    rect.height.toFloat(),
                    rect.height.toFloat(),
                    rect.height.toFloat()
                )
            )
        } finally {
            g2.dispose()
        }
    }

    override fun paintLookBorder(g: Graphics, rect: Rectangle, color: Color) {

    }


}
