package com.troy.theme

import com.intellij.ui.components.JBScrollBar
import javax.swing.JComponent
import javax.swing.plaf.ScrollBarUI

class AScrollBar : JBScrollBar() {

    companion object {

        fun createUI(c: JComponent): ScrollBarUI {
            return createUI(c, false)
        }

        fun createUI(c: JComponent, isThin: Boolean): ScrollBarUI {
            return AScrollBarUI()
        }

    }

}
