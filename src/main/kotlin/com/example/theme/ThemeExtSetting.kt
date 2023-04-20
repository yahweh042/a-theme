package com.example.theme

import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
import javax.swing.JTextField

class ThemeExtSetting : SearchableConfigurable {

    private var panel = panel {
        group("List") {
            row("RowHeight:") {
                intTextField()
            }
        }
    }
    private var listRowHeight: JTextField? = null

    override fun createComponent(): JComponent {
        return JBScrollPane(this.panel)
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun apply() {
        val themeExtConfig = ThemeExtConfig.getInstance()
        themeExtConfig.listRowHeight = this.listRowHeight!!.text.toInt()
        themeExtConfig.applyChange()
    }

    override fun getDisplayName(): String = "ThemeExtSetting"

    override fun getId(): String = "ThemeExtSetting"
}
