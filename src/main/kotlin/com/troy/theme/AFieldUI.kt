package com.troy.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaTextBorder
import com.intellij.ide.ui.laf.darcula.ui.DarculaTextFieldUI
import java.awt.Graphics
import javax.swing.JComponent

class AFieldUI : DarculaTextFieldUI() {

    override fun paintBackground(g: Graphics) {

    }



    companion object {

        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(c: JComponent): AFieldUI {
            return AFieldUI()
        }
    }

}
