package com.troy.theme

import javax.swing.JComponent
import javax.swing.plaf.basic.BasicPopupMenuUI

class APopupMenuUI : BasicPopupMenuUI() {

    companion object {

        @JvmStatic
        fun createUI(c: JComponent): APopupMenuUI {
            return APopupMenuUI()
        }
    }

}
