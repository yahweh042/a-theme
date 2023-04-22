package com.example.theme

import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.impl.segmentedActionBar.SegmentedActionToolbarComponent.Companion.isCustomBar
import com.intellij.openapi.actionSystem.impl.segmentedActionBar.SegmentedActionToolbarComponent.Companion.paintButtonDecorations
import com.intellij.ui.JBColor
import com.intellij.ui.scale.JBUIScale
import com.intellij.util.ObjectUtils
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.MacUIUtil
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.RoundRectangle2D
import javax.swing.AbstractButton
import javax.swing.JComponent
import javax.swing.UIManager


class MaterialButtonUI : DarculaButtonUI() {

    override fun installDefaults(b: AbstractButton) {
        super.installDefaults(b)
        b.isRolloverEnabled = true
    }

    override fun getDarculaButtonSize(component: JComponent, prefSize: Dimension): Dimension {
        val insets = component.insets
        val size = ObjectUtils.notNull(prefSize, JBUI.emptySize())
        return if (UIUtil.isHelpButton(component) || isSquare(component)) {
            val helpDiam = HELP_BUTTON_DIAMETER.get()
            Dimension(size.width.coerceAtLeast(helpDiam + insets.left + insets.right),
                    size.height.coerceAtLeast(helpDiam + insets.top + insets.bottom))
        } else {
            val width = if (isComboAction(component)) size.width else (HORIZONTAL_PADDING.get() * 2 + size.width).coerceAtLeast(MINIMUM_BUTTON_WIDTH.get() + insets.left + insets.right)
            val height = size.height.coerceAtLeast((if (isSmallVariant(component)) ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE.height else minimumHeight) + insets.top + insets.bottom)
            Dimension(width, height)
        }
    }

    override fun paintContents(g: Graphics, b: AbstractButton) {
        super.paintContents(g, b)
    }

    override fun paintDecorations(g: Graphics2D, c: JComponent): Boolean {
        val button = c as AbstractButton
        if (!button.isContentAreaFilled) {
            return true
        }
        val r = Rectangle(button.size)
        if (isCustomBar(c)) {
            return paintButtonDecorations(g, c, JBColor.BLACK)
        }
        JBInsets.removeFrom(r, if (isSmallVariant(c) || isGotItButton(c)) c.getInsets() else JBUI.insets(1))
        if (UIUtil.isHelpButton(c)) {
            g.paint = UIUtil.getGradientPaint(0f, 0f, buttonColorStart, 0f, r.height.toFloat(), buttonColorEnd)
            val diam = HELP_BUTTON_DIAMETER.get()
            val x = r.x + (r.width - diam) / 2
            val y = r.x + (r.height - diam) / 2
            g.fill(Ellipse2D.Float(x.toFloat(), y.toFloat(), diam.toFloat(), diam.toFloat()))
            AllIcons.Actions.Help.paintIcon(c, g, x + JBUIScale.scale(3), y + JBUIScale.scale(3))
            return false
        }

        val g2 = g.create() as Graphics2D
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    if (MacUIUtil.USE_QUARTZ) RenderingHints.VALUE_STROKE_PURE else RenderingHints.VALUE_STROKE_NORMALIZE)
            g2.translate(r.x, r.y)
            val bw: Float = if (isSmallVariant(c) || isGotItButton(c)) 0f else DarculaUIUtil.BW.float
            val arc = if (isTag(c)) r.height - bw * 2 else DarculaUIUtil.BUTTON_ARC.float
            if (!c.hasFocus() && !isSmallVariant(c) && c.isEnabled() && UIManager.getBoolean("Button.paintShadow")) {
                val shadowColor: Color = JBColor.namedColor("Button.shadowColor", JBColor.namedColor("Button.darcula.shadowColor",
                        JBColor(Color(-0x595959cd, true), Color(0x36363680, true))))
                val shadowWidth = JBUIScale.scale(JBUI.getInt("Button.shadowWidth", 2))
                g2.color = if (isDefaultButton(c)) JBColor.namedColor("Button.default.shadowColor", shadowColor) else shadowColor
                g2.fill(RoundRectangle2D.Float(bw, bw + shadowWidth, r.width - bw * 2, r.height - bw * 2, arc, arc))
            }
            if (button.isEnabled) {
                if (button.isSelected) {
                    g2.color = JBColor.BLUE
                }
                g2.fill(RoundRectangle2D.Float(bw, bw, r.width - bw * 2, r.height - bw * 2, arc, arc))
            }
        } finally {
            g2.dispose()
        }
        return true
    }


    companion object {
        @JvmStatic
        fun createUI(component: JComponent): MaterialButtonUI {
            return MaterialButtonUI()
        }
    }

}
