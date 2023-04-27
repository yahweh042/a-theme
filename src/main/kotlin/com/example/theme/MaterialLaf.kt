package com.example.theme

import com.intellij.ide.ui.laf.darcula.DarculaLaf
import javax.swing.UIDefaults


class MaterialLaf : DarculaLaf() {

    override fun getName(): String = "Dark"

    override fun getDefaults(): UIDefaults {
        val defaults = super.getDefaults()

        defaults["Button.arc"] = 0
        defaults["Component.arc"] = 0

        // defaults["ListUI"] = AList::class.java.name
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
