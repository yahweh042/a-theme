package com.troy.theme

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
            BasicStroke(1.0f)
        } else {
            BasicStroke(
                1.0f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                0.0f,
                floatArrayOf(1.0f, 2.0f),
                0.0f
            )
        }
    }

    fun setBorderStyle(g: Graphics2D, enabled: Boolean, focused: Boolean) {
        g.color = getBorderColor(enabled, focused)
        g.stroke = getBorderStroke(enabled)
    }

    fun setHandCursor(c : JComponent) {
        c.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
    }

}
