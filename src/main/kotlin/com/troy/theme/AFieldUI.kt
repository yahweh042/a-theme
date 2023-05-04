package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaTextFieldUI
import java.awt.Graphics
import javax.swing.JComponent
import javax.swing.text.JTextComponent

class AFieldUI : DarculaTextFieldUI() {

    override fun paintDarculaBackground(g: Graphics, component: JTextComponent) {

    }


    companion object {

        @JvmStatic
        fun createUI(c: JComponent): AFieldUI = AFieldUI()
    }

}
