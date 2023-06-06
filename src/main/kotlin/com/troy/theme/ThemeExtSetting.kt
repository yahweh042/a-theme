package com.troy.theme

import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.toNullableProperty
import javax.swing.JComponent

class ThemeExtSetting : SearchableConfigurable {

    private val items = arrayListOf("Material", "Default")
    private var state = ThemeExtConfigState.getInstance().state

    private val panel = panel {
        group("List") {
            row("RowHeight:") {
                intTextField().bindIntText(state::listRowHeight)
            }
        }
        group("Tree") {
            twoColumnsRow(
                {
                    intTextField().label("RowHeight:").bindIntText(state.treeState::rowHeight)
                },
                {
                    intTextField().label("SelectionArc:").bindIntText(state.treeState::selectionArc)
                }
            )
        }
        group("StatusBar") {
            row("Height:") {
                intTextField().bindIntText(state::statusBarHeight)
            }
        }
        group("Button") {
            twoColumnsRow(
                {
                    comboBox(items).label("Style:").bindItem(state.buttonState::style.toNullableProperty())

                },
                {
                    intTextField().label("Arc:").bindIntText(state.buttonState::arc)
                }
            )
        }
        group("ComboBox") {
            twoColumnsRow(
                {
                    comboBox(items).label("Style:").bindItem(state.comboBoxState::style.toNullableProperty())
                },
                {
                    intTextField().label("Arc:").bindIntText(state.comboBoxState::arc)
                })
        }
        group("Field") {
            row("Style:") {
                comboBox(items).bindItem(state::fieldStyle.toNullableProperty())
            }
        }
        group("PopupMenu") {
            twoColumnsRow(
                {
                    intTextField().label("BorderCornerRadius:").bindIntText(state.popupMenuState::borderCornerRadius)
                },
                {
                    intTextField().label("SelectionArc:").bindIntText(state.popupMenuState::selectionArc)
                }
            )
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
