package com.example.theme

import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.panel
import javax.swing.JButton
import javax.swing.JTextField

class ThemeExtToolWindow(
        private val project: Project,
        private val toolWindow: ToolWindow
) : SimpleToolWindowPanel(true, true), DataProvider {

    private lateinit var listRowHeight: JTextField
    private lateinit var treeRowHeight: JTextField

    private val themeExtConfig = ThemeExtConfig.getInstance()

    private val panel = panel {
        row("ListRowHeight:") {
            listRowHeight = JTextField(themeExtConfig.listRowHeight.toString())
            cell(listRowHeight)
        }
        row("TreeRowHeight:") {
            treeRowHeight = JTextField(themeExtConfig.treeRowHeight.toString())
            cell(treeRowHeight)
        }
        row {
            button("Apply") {
                themeExtConfig.listRowHeight = listRowHeight.text.toInt()
                themeExtConfig.treeRowHeight = treeRowHeight.text.toInt()
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
