package com.example.theme

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class ThemeExtToolWindowFactory: ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val title = "ThemeExtConfig"
        toolWindow.title = title
        toolWindow.stripeTitle = title
        val contentManager = toolWindow.contentManager
        val content = contentManager.factory.createContent(ThemeExtToolWindow(project, toolWindow), null, true)
        contentManager.addContent(content)
    }

    override fun init(toolWindow: ToolWindow) {
    }
}
