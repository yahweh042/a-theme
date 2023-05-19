package com.troy.theme

import com.intellij.ide.ui.laf.intellij.IdeaPopupMenuUI
import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import java.awt.*
import javax.swing.JComponent

object AThemeUtils {

    private fun getBorderColor(enabled: Boolean, focused: Boolean): Color {
        return if (enabled) {
            if (focused) JBUI.CurrentTheme.Focus.focusColor() else JBColor.namedColor(
                "Component.borderColor",
                JBColor.namedColor("Outline.color", Gray.xBF)
            )
        } else {
            JBColor.namedColor("Component.disabledBorderColor", JBColor.namedColor("Outline.disabledColor", Gray.xCF))
        }
    }

    private fun getBorderStroke(enabled: Boolean): Stroke {
        return if (enabled) {
            BasicStroke(1.5f)
        } else {
            BasicStroke(
                1.5f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                0.0f,
                floatArrayOf(2.0f, 4.0f),
                0.0f
            )
        }
    }

    fun patchMenuItemPreferredSize(c : Component, preferredSize: Dimension): Dimension {
        val outerInsets = if (IdeaPopupMenuUI.isPartOfPopupMenu(c)) JBUI.CurrentTheme.PopupMenu.Selection.outerInsets() else JBUI.CurrentTheme.Menu.Selection.outerInsets()
        return Dimension(preferredSize.width, JBUI.CurrentTheme.List.rowHeight() + outerInsets.height())
    }

    fun setBorderStyle(g: Graphics2D, enabled: Boolean, focused: Boolean) {
        g.color = getBorderColor(enabled, focused)
        g.stroke = getBorderStroke(enabled)
    }

    fun setHandCursor(c: JComponent) {
        c.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
    }

}
