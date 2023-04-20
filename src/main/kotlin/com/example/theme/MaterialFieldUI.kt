package com.example.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaTextFieldUI
import java.awt.Graphics
import javax.swing.JComponent

class MaterialFieldUI : DarculaTextFieldUI() {

    override fun paintBackground(g: Graphics?) {

    }


    companion object {

        @JvmStatic
        fun createUI(c: JComponent): MaterialFieldUI {
            return MaterialFieldUI()
        }
    }

}