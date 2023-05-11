package com.troy.theme

import com.intellij.ui.plaf.beg.IdeaMenuUI
import java.awt.Color
import java.awt.Graphics
import javax.swing.ButtonModel
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.JMenu

class AMenuUI : IdeaMenuUI() {

    override fun fillOpaque(
        g: Graphics?,
        comp: JComponent?,
        jMenu: JMenu?,
        buttonmodel: ButtonModel?,
        allowedIcon: Icon?,
        mainColor: Color?
    ) {

    }
    companion object {

        fun createUI(component: JComponent): AMenuUI {
            return AMenuUI()
        }
    }
}
