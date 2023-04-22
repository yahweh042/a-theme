package com.example.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.openapi.actionSystem.impl.IdeaActionButtonLook
import java.awt.*
import java.awt.geom.Arc2D
import java.awt.geom.Path2D
import java.awt.geom.RoundRectangle2D

class MaterialActionButtonLook: IdeaActionButtonLook() {

    override fun paintLookBorder(g: Graphics, rect: Rectangle, color: Color) {
        val g2 = g.create() as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)

        try {
            g2.color = color
            val arc = buttonArc.float
            val lw = DarculaUIUtil.LW.float
            val border: Path2D = Path2D.Float(Path2D.WIND_EVEN_ODD)
            border.append(Arc2D.Float(), false)
            border.append(RoundRectangle2D.Float(rect.x + lw, rect.y + lw, rect.width - lw * 2, rect.height - lw * 2, arc - lw, arc - lw), false)
            g2.fill(border)
        } finally {
            g2.dispose()
        }
    }

}