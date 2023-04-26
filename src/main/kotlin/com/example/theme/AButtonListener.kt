package com.example.theme

import com.intellij.ui.JBColor
import javax.swing.AbstractButton
import javax.swing.JButton
import javax.swing.event.ChangeEvent
import javax.swing.plaf.basic.BasicButtonListener

class AButtonListener(button: AbstractButton) : BasicButtonListener(button) {

    private var isRollover = false

    override fun stateChanged(e: ChangeEvent) {
        if (e.source is JButton) {
            val jButton = e.source as JButton
            val model = jButton.model
            if (model.isRollover != isRollover) {
                isRollover = model.isRollover
                if (isRollover) jButton.putClientProperty("JButton.backgroundColor", JBColor.BLUE) else  jButton.putClientProperty("JButton.backgroundColor", JBColor.RED)
            }
        }
        super.stateChanged(e)
    }

}
