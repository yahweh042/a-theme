package com.troy.theme

import com.intellij.ide.ui.laf.darcula.ui.DarculaOptionButtonUI

class AOptionButtonUI : DarculaOptionButtonUI() {

    override fun createMainButton(): MainButton = object : MainButton() {

    }

    companion object {
        @JvmStatic
        fun createUI(): AOptionButtonUI = AOptionButtonUI()
    }

}
