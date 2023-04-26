package com.example.theme

import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.util.ui.JBUI
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.Icon
import javax.swing.UIDefaults


class MaterialLaf : DarculaLaf() {

    override fun getName(): String = "Dark"

    override fun getDefaults(): UIDefaults {
        val defaults = super.getDefaults()



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

        defaults["ListUI"] = AList::class.java.name
        defaults[AList::class.java.name] = AList::class.java

        defaults["ComboBoxUI"] = AComboBoxUI::class.java.name
        defaults[AComboBoxUI::class.java.name] = AComboBoxUI::class.java

        val fieldBorder = AFieldBorder()

        defaults["FormattedTextField.border"] = fieldBorder
        defaults["PasswordField.border"] = fieldBorder
        defaults["TextField.border"] = fieldBorder
        defaults["EditorTextField.border"] = fieldBorder


        defaults["FormattedTextFieldUI"] = AFieldUI::class.java.name
        defaults["PasswordFieldUI"] = AFieldUI::class.java.name
        defaults["TextFieldUI"] = AFieldUI::class.java.name
        defaults[AFieldUI::class.java.name] = AFieldUI::class.java

        return defaults
    }

}
