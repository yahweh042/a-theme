package com.example.theme

import com.intellij.internal.ui.gridLayoutTestAction.createTabPanel
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class ThemeExtSetting : SearchableConfigurable {

    private val items = arrayListOf("Material", "Default")
    private var state = ThemeExtConfigState.getInstance().state

    private val listPanel = panel {
        row("ListRowHeight:") {
            intTextField().bindIntText(state::listRowHeight)
        }
        row("TreeRowHeight:") {
            intTextField().bindIntText(state::treeRowHeight)
        }
        row("ButtonStyle:") {
            comboBox(items).bindItem(state::buttonStyle)
        }
        row("ComboBoxStyle:") {
            comboBox(items).bindItem(state::comboBoxStyle)
        }
    }

    override fun createComponent(): JComponent {
        return listPanel
    }

    override fun isModified(): Boolean {
        return listPanel.isModified()
    }

    override fun apply() {
        listPanel.apply()
        ThemeExtConfigState.getInstance().applyChange()
    }

    override fun reset() {
        listPanel.reset()
    }

    override fun getDisplayName(): String = "ThemeExtSetting"

    override fun getId(): String = "ThemeExtSetting"
}
