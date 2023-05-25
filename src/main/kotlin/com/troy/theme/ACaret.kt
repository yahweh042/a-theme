package com.troy.theme

import java.awt.Graphics
import javax.swing.text.DefaultCaret


class ACaret : DefaultCaret() {

    init {
        blinkRate = 500
    }

    override fun paint(g: Graphics) {
        if (isVisible) {
            try {
                val rectangle = component.ui.modelToView(component, dot, dotBias)
                val x = rectangle.x + (rectangle.width / 2)
                val y = rectangle.y + (rectangle.height / 2)
                g.color = component.caretColor
                g.translate(x, y)

                g.drawLine(x, y, x, 0)
                g.drawLine(x, y, x, height)

            } catch (e: Exception) {
                // Ignore error
            }
        }
    }

}
