package com.troy.theme

import com.intellij.ui.JBColor
import java.awt.event.MouseEvent
import javax.swing.AbstractButton
import javax.swing.plaf.basic.BasicButtonListener

class AButtonListener(button: AbstractButton) : BasicButtonListener(button) {

    private var isRollover = false

    // override fun stateChanged(e: ChangeEvent) {
    //     if (e.source is JButton) {
    //         val jButton = e.source as JButton
    //         val model = jButton.model
    //         if (model.isRollover != isRollover) {
    //             isRollover = model.isRollover
    //             if (isRollover) jButton.putClientProperty("JButton.backgroundColor", JBColor.BLUE) else  jButton.putClientProperty("JButton.backgroundColor", JBColor.RED)
    //         }
    //     }
    //     super.stateChanged(e)
    // }

    override fun mouseEntered(e: MouseEvent) {
        super.mouseEntered(e)
        val b = e.source as AbstractButton
        if (b.isEnabled) {
            b.background = JBColor.RED
            b.repaint()
        }
    }

    override fun mouseExited(e: MouseEvent) {
        super.mouseExited(e)
        val b = e.source as AbstractButton
        if (b.isEnabled) {
            b.background = JBColor.BLUE
            b.repaint()
        }
    }


}
