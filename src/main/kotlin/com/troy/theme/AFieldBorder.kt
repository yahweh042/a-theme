package com.troy.theme

import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaTextBorder
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.MacUIUtil
import java.awt.*
import java.awt.geom.Path2D
import javax.swing.JComponent
import javax.swing.text.JTextComponent

class AFieldBorder : DarculaTextBorder() {

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
        if ((c as JComponent).getClientProperty("JTextField.Search.noBorderRing") == true) {
            return
        }
        val r = Rectangle(x, y, width, height)
        val focused = isFocused(c)
        if (DarculaUIUtil.isTableCellEditor(c)) {
            DarculaUIUtil.paintCellEditorBorder(g as Graphics2D, c, r, focused)
        } else {
            paintSearchArea(g as Graphics2D, r, c as JTextComponent, false)
        }
    }

    override fun paintSearchArea(g: Graphics2D, r: Rectangle, c: JTextComponent, fillBackground: Boolean) {
        val g2 = g.create() as Graphics2D
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
            JBInsets.removeFrom(r, JBUI.insets(1))
            g2.translate(r.x, r.y)
            val arc = DarculaUIUtil.COMPONENT_ARC.get().toFloat()
            val path: Path2D = Path2D.Float(Path2D.WIND_EVEN_ODD)
            path.append(AThemeUtils.getOuterShape(r, 1f, arc), false)
            path.append(AThemeUtils.getInnerShape(r, 1f, 1f, arc), false)
            g2.color = DarculaUIUtil.getOutlineColor(c.isEnabled, c.hasFocus())
            g2.fill(path)
        } finally {
            g2.dispose()
        }
    }

}
