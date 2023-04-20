package com.example.theme

import javax.swing.JComponent
import javax.swing.plaf.basic.BasicListUI

class MaterialList : BasicListUI() {

    companion object {

        @JvmStatic
        fun createUI(component: JComponent) = MaterialList()

    }

}