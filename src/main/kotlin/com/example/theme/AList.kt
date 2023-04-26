package com.example.theme

import javax.swing.JComponent
import javax.swing.plaf.basic.BasicListUI

class AList : BasicListUI() {

    companion object {

        @JvmStatic
        fun createUI(component: JComponent) = AList()

    }

}
