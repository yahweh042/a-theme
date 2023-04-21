package com.example.theme

import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class ThemeExtSetting : SearchableConfigurable {

    private val items = arrayListOf("Material", "Default")
    private val themeExtConfig get() = ThemeExtConfigState.getInstance()
    private var isModify = false

    private val panel = panel {
        row("ListRowHeight:") {
            textField().bindIntText(themeExtConfig::getListRowHeight, themeExtConfig::setListRowHeight)
        }
//        row("TreeRowHeight:") {
//            textField().bindIntText(
//                getter = { themeExtConfig.treeRowHeight },
//                setter = {
//                    isModify = true
//                    themeExtConfig.treeRowHeight = it
//                })
//        }
//        row("ButtonStyle:") {
//            comboBox(items).bindItem(
//                getter = { themeExtConfig.buttonStyle },
//                setter = {
//                    if (it != null) {
//                        themeExtConfig.buttonStyle = it
//                    }
//                }
//            )
//        }
//        row("ComboBoxStyle:") {
//            comboBox(items).bindItem(
//                getter = { themeExtConfig.comboBoxStyle },
//                setter = {
//                    isModify = true
//                    themeExtConfig.comboBoxStyle = it!!
//                }
//            )
//            comboBox(items).applyToComponent {
//            }
//        }
    }

    override fun createComponent(): JComponent {
        return JBScrollPane(this.panel)
    }

    override fun isModified(): Boolean {
        return panel.isModified()
    }

    override fun apply() {
        themeExtConfig.applyChange()
    }

    override fun getDisplayName(): String = "ThemeExtSetting"

    override fun getId(): String = "ThemeExtSetting"
}
