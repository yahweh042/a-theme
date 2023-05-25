package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaTextFieldUI
import java.awt.Graphics
import javax.swing.JComponent
import javax.swing.text.Caret
import javax.swing.text.JTextComponent

class AFieldUI : DarculaTextFieldUI() {

    override fun paintBackground(g: Graphics) {
    }

    override fun createCaret(): Caret {
        val caret = super.createCaret()
        caret.blinkRate = 0
        return caret
    }

    companion object {

        @JvmStatic
        fun createUI(c: JComponent): AFieldUI {
            return AFieldUI()
        }
    }

}
