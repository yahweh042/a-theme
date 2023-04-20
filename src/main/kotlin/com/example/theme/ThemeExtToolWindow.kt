package com.example.theme

import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel

class ThemeExtToolWindow(
    private val project: Project,
    private val toolWindow: ToolWindow
) : SimpleToolWindowPanel(true, true), DataProvider {

    private val themeExtConfig = ThemeExtConfig.getInstance()
    private val items = arrayListOf("Material", "Default")

    private val panel = panel {
        row("ListRowHeight:") {
            textField().bindIntText(
                getter = { themeExtConfig.listRowHeight },
                setter = { themeExtConfig.listRowHeight = it })
        }
        row("TreeRowHeight:") {
            textField().bindIntText(
                getter = { themeExtConfig.treeRowHeight },
                setter = { themeExtConfig.treeRowHeight = it })
        }
        row("ButtonStyle:") {
            comboBox(items).bindItem(
                getter = { themeExtConfig.buttonStyle },
                setter = {
                    if (it != null) {
                        themeExtConfig.buttonStyle = it
                    }
                }
            )
        }
        row("ComboBoxStyle:") {
            comboBox(items).bindItem(
                getter = { themeExtConfig.comboBoxStyle },
                setter = { value -> themeExtConfig.comboBoxStyle = value!!}
            )
            comboBox(items).applyToComponent {
            }
        }
        row {
            button("Apply") {
                themeExtConfig.applyChange()
            }
            cell()
        }
    }

    init {
        val jbScrollPane = JBScrollPane(panel)

        setContent(jbScrollPane)

    }

}
