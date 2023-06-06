package com.troy.theme

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


class AButtonUI : DarculaButtonUI() {

    override fun installDefaults(b: AbstractButton) {
        super.installDefaults(b)
        AThemeUtils.setHandCursor(b)
    }

    override fun getDarculaButtonSize(component: JComponent, prefSize: Dimension): Dimension {
        val insets = component.insets
        val size = ObjectUtils.notNull(prefSize, JBUI.emptySize())
        return if (UIUtil.isHelpButton(component) || isSquare(component)) {
            val helpDiam = HELP_BUTTON_DIAMETER.get()
            Dimension(
                size.width.coerceAtLeast(helpDiam + insets.left + insets.right),
                size.height.coerceAtLeast(helpDiam + insets.top + insets.bottom)
            )
        } else {
            val width =
                if (isComboAction(component)) size.width else (HORIZONTAL_PADDING.get() * 2 + size.width).coerceAtLeast(
                    MINIMUM_BUTTON_WIDTH.get() + insets.left + insets.right
                )
            val height =
                size.height.coerceAtLeast((if (isSmallVariant(component)) ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE.height else minimumHeight) + insets.top + insets.bottom)
            Dimension(width, height)
        }
    }

    override fun paintContents(g: Graphics, b: AbstractButton) {
        super.paintContents(g, b)
    }

    override fun paintDecorations(g: Graphics2D, c: JComponent): Boolean {
        val button = c as AbstractButton
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
            g2.setRenderingHint(
                RenderingHints.KEY_STROKE_CONTROL,
                if (MacUIUtil.USE_QUARTZ) RenderingHints.VALUE_STROKE_PURE else RenderingHints.VALUE_STROKE_NORMALIZE
            )
            g2.translate(r.x, r.y)
            val bw: Float = if (isSmallVariant(c) || isGotItButton(c)) 0f else DarculaUIUtil.BW.float
            val arc = if (isTag(c)) r.height - bw * 2 else DarculaUIUtil.BUTTON_ARC.float
            if (c.isEnabled) {
                g2.paint =
                    if (c.hasFocus()) JBUI.CurrentTheme.Button.focusBorderColor(isDefaultButton(c)) else getBackground(
                        c,
                        r
                    )
                g2.fill(RoundRectangle2D.Float(0f, 0f, r.width.toFloat(), r.height.toFloat(), arc, arc))
            }
        } finally {
            g2.dispose()
        }

        return true
    }

    private fun getBackground(c: JComponent, r: Rectangle): Paint {
        val backgroundColor = c.getClientProperty("JButton.backgroundColor") as Color?
        return backgroundColor ?: when {
            isDefaultButton(c) -> UIUtil.getGradientPaint(
                0f,
                0f,
                defaultButtonColorStart,
                0f,
                r.height.toFloat(),
                defaultButtonColorEnd
            )

            isSmallVariant(c) -> JBColor.namedColor(
                "ComboBoxButton.background",
                JBColor.namedColor("Button.darcula.smallComboButtonBackground", UIUtil.getPanelBackground())
            )

            isGotItButton(c) -> UIUtil.getGradientPaint(
                0f,
                0f,
                getGotItButtonColorStart(c),
                0f,
                r.height.toFloat(),
                getGotItButtonColorEnd(c)
            )

            else -> UIUtil.getGradientPaint(
                0f,
                0f,
                buttonColorStart,
                0f,
                r.height.toFloat(),
                buttonColorEnd
            )
        }
    }

    private fun getGotItButtonColorStart(c: Component): Color {
        return if (isContrastGotIt(c)) {
            JBUI.CurrentTheme.GotItTooltip.buttonBackgroundContrast()
        } else JBColor.namedColor("GotItTooltip.Button.startBackground", JBUI.CurrentTheme.Button.buttonColorStart())
    }

    private fun getGotItButtonColorEnd(c: Component): Color {
        return if (isContrastGotIt(c)) {
            JBUI.CurrentTheme.GotItTooltip.buttonBackgroundContrast()
        } else JBColor.namedColor("GotItTooltip.Button.endBackground", JBUI.CurrentTheme.Button.buttonColorEnd())
    }

    companion object {
        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(component: JComponent): AButtonUI = AButtonUI()
    }

}
