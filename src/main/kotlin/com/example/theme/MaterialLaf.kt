package com.example.theme

import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.ide.ui.laf.darcula.ui.DarculaTextFieldUI
import com.intellij.util.ui.JBUI
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.Icon
import javax.swing.UIDefaults


class MaterialLaf : DarculaLaf() {

    override fun getDefaults(): UIDefaults {
        val defaults = super.getDefaults()

        defaults["ButtonUI"] = MaterialButtonUI::class.java.name
        defaults[MaterialButtonUI::class.java.name] = MaterialButtonUI::class.java

        defaults["Button.border"] = MaterialButtonBorder()

        defaults["Button.arc"] = 0
        defaults["Component.arc"] = 0

        defaults["ActionButton.backgroundIcon"] = object : Icon {
            override fun paintIcon(c: Component?, g: Graphics?, x: Int, y: Int) {
                val create: Graphics2D = g!!.create() as Graphics2D
                try {
                    create.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    create.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
                    create.translate(x, y)
                    create.color = JBUI.CurrentTheme.ActionButton.pressedBackground()
                    if (iconWidth > 28) {
                        create.fill3DRect(0, 0, iconWidth, iconHeight, true)
                    } else {
                        create.fillOval(0, 0, iconWidth, iconHeight)
                    }
                } finally {
                    create.dispose()
                }
            }

            override fun getIconWidth(): Int = JBUI.scale(18)

            override fun getIconHeight(): Int = JBUI.scale(18)

        }

        defaults["ListUI"] = MaterialList::class.java.name
        defaults[MaterialList::class.java.name] = MaterialList::class.java

        defaults["ComboBoxUI"] = MaterialComboBoxUI::class.java.name
        defaults[MaterialComboBoxUI::class.java.name] = MaterialComboBoxUI::class.java

        val fieldBorder = MaterialFieldBorder()

        defaults["FormattedTextField.border"] = fieldBorder
        defaults["PasswordField.border"] = fieldBorder
        defaults["TextField.border"] = fieldBorder
        defaults["EditorTextField.border"] = fieldBorder


        defaults["FormattedTextFieldUI"] = MaterialFieldUI::class.java.name
        defaults["PasswordFieldUI"] = MaterialFieldUI::class.java.name
        defaults["TextFieldUI"] = MaterialFieldUI::class.java.name
        defaults[MaterialFieldUI::class.java.name] = MaterialFieldUI::class.java

        return defaults
    }

}
