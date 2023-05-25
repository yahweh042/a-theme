package com.troy.theme

import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class ThemeExtSetting : SearchableConfigurable {

    private val items = arrayListOf("Material", "Default")
    private var state = ThemeExtConfigState.getInstance().state

    private val panel = panel {
        group("List") {
            row("ListRowHeight:") {
                intTextField().bindIntText(state::listRowHeight)
            }
        }
        row("TreeRowHeight:") {
            intTextField().bindIntText(state::treeRowHeight)
        }
        row("StatusBarHeight:") {
            intTextField().bindIntText(state::statusBarHeight)
        }
        row("ButtonStyle:") {
            comboBox(items).bindItem(state::buttonStyle)
        }
        row("ComboBoxStyle:") {
            comboBox(items).bindItem(state::comboBoxStyle)
        }
        group("PopupMenu") {
            row("BorderCornerRadius") {
                intTextField().bindIntText(state.popupMenuState::borderCornerRadius)
            }
            row("SelectionCornerRadius") {
                intTextField().bindIntText(state.popupMenuState::selectionArc)
            }
        }
    }

    override fun createComponent(): JComponent {
        return panel
    }

    override fun isModified(): Boolean {
        return panel.isModified()
    }

    override fun apply() {
        panel.apply()
        ThemeExtConfigState.getInstance().applyChange()
    }

    override fun reset() {
        panel.reset()
    }

    override fun getDisplayName(): String = "ThemeExtSetting"

    override fun getId(): String = "ThemeExtSetting"
}
