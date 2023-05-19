package com.troy.theme

import com.intellij.ui.plaf.beg.IdeaMenuUI
import java.awt.Dimension
import javax.swing.JComponent

class AMenuUI : IdeaMenuUI() {

    override fun getPreferredSize(c: JComponent): Dimension {
        val preferredSize = super.getPreferredSize(c)
        return patchPreferredSize(c, preferredSize)
    }

    companion object {


        @JvmStatic
        fun createUI(component: JComponent): AMenuUI {
            return AMenuUI()
        }
    }
}
