package com.troy.theme

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.laf.darcula.ui.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.ui.scale.JBUIScale
import com.intellij.util.xmlb.XmlSerializerUtil
import javax.swing.UIDefaults
import javax.swing.UIManager

@State(name = "ThemeExtConfig", storages = [Storage("theme_ext_config.xml")])
class ThemeExtConfigState : PersistentStateComponent<ThemeExtConfigState.ThemeExtConfig> {

    private val themeExtConfig = ThemeExtConfig()

    override fun getState(): ThemeExtConfig = themeExtConfig

    override fun loadState(state: ThemeExtConfig) {
        XmlSerializerUtil.copyBean(state, themeExtConfig)
    }

    fun applyChange() {
        val defaults = UIManager.getDefaults()
        defaults["List.rowHeight"] = JBUIScale.scale(state.listRowHeight)

        applyButtonStyle(defaults)
        applyComboBoxStyle(defaults)

        applyCheckBoxStyle(defaults)
        applyRadioButtonStyle(defaults)

        applyPopupMenuState(defaults)

        // defaults["MenuItem.evenHeight"] = true

        // defaults["ListUI"] = AList::class.java.name
        // defaults[AList::class.java.name] = AList::class.java


        defaults["IdeStatusBarUI"] = AStatusBarUI::class.java.name
        defaults[AStatusBarUI::class.java.name] = AStatusBarUI::class.java

        // defaults["PopupMenuUI"] = APopupMenuUI::class.java.name
        // defaults[APopupMenuUI::class.java.name] = APopupMenuUI::class.java

        // val menuBorder = AMenuItemBorder()
        // defaults["MenuItem.border"] = menuBorder
        // defaults["Menu.border"] = menuBorder

        defaults["ScrollBar"] = AScrollBar::class.java.name
        defaults[AScrollBar::class.java.name] = AScrollBar::class.java

        applyFieldStyle(defaults)
        applyTreeState(defaults)
        LafManager.getInstance().updateUI()
    }

    private fun applyButtonStyle(defaults: UIDefaults) {
        defaults["ButtonUI"] = when (state.buttonState.style) {
            "Material" -> AButtonUI::class.java.name
            else -> DarculaButtonUI::class.java.name
        }
        defaults["Button.border"] = when (state.buttonState.style) {
            "Material" -> AButtonBorder()
            else -> DarculaButtonPainter()
        }
        defaults[AButtonUI::class.java.name] = AButtonUI::class.java
        defaults[DarculaButtonUI::class.java.name] = DarculaButtonUI::class.java

        defaults["Button.arc"] = state.buttonState.arc
    }

    private fun applyComboBoxStyle(defaults: UIDefaults) {
        defaults["ComboBoxUI"] = when (state.comboBoxState.style) {
            "Material" -> AComboBoxUI::class.java.name
            else -> DarculaComboBoxUI::class.java.name
        }
        defaults[AComboBoxUI::class.java.name] = AComboBoxUI::class.java
        defaults[DarculaComboBoxUI::class.java.name] = DarculaComboBoxUI::class.java
        defaults["ComboBox.arc"] = state.comboBoxState.arc
    }

    private fun applyRadioButtonStyle(defaults: UIDefaults) {
        defaults["RadioButtonUI"] = ARadioButtonUI::class.java.name
        defaults[ARadioButtonUI::class.java.name] = ARadioButtonUI::class.java
    }

    private fun applyCheckBoxStyle(defaults: UIDefaults) {
        defaults["CheckBoxUI"] = ACheckBoxUI::class.java.name
        defaults[ACheckBoxUI::class.java.name] = ACheckBoxUI::class.java
        defaults["CheckBox.border"] = ACheckBoxBorder()
    }

    private fun applyFieldStyle(defaults: UIDefaults) {
        val fieldBorder = when (state.fieldStyle) {
            "Material" -> AFieldBorder()
            else -> DarculaTextBorder()
        }
        defaults["FormattedTextField.border"] = fieldBorder
        defaults["PasswordField.border"] = fieldBorder
        defaults["TextField.border"] = fieldBorder
        defaults["EditorTextField.border"] = fieldBorder

        val className = when (state.fieldStyle) {
            "Material" -> AFieldUI::class.java.name
            else -> DarculaTextFieldUI::class.java.name
        }
        defaults["FormattedTextFieldUI"] = className
        defaults["PasswordFieldUI"] = className
        defaults["TextFieldUI"] = className

        defaults[DarculaTextFieldUI::class.java.name] = DarculaTextFieldUI::class.java
        defaults[AFieldUI::class.java.name] = AFieldUI::class.java
    }

    private fun applyPopupMenuState(defaults: UIDefaults) {
        defaults["PopupMenu.borderCornerRadius"] = state.popupMenuState.borderCornerRadius
        defaults["PopupMenu.Selection.arc"] = state.popupMenuState.selectionArc
        defaults["Popup.Selection.arc"] = 5
        defaults["Button.ToolWindow.arc"] = 5
        defaults["Menu.Selection.arc"] = 5
        defaults["GotItTooltip.arc"] = 5
        defaults["Notification.arc"] = 5
        defaults["ToolTip.arc"] = 5
    }

    private fun applyTreeState(defaults: UIDefaults) {
        defaults["Tree.rowHeight"] = state.treeState.rowHeight
        defaults["Tree.Selection.arc"] = state.treeState.selectionArc
    }

    companion object {

        @JvmStatic
        fun getInstance(): ThemeExtConfigState =
            ApplicationManager.getApplication().getService(ThemeExtConfigState::class.java)

    }

    data class ThemeExtConfig(
        var listRowHeight: Int = 24,
        var statusBarHeight: Int = 24,
        var fieldStyle: String = "Material",
        var popupMenuState: PopupMenuState = PopupMenuState(),
        var comboBoxState: ComboBoxState = ComboBoxState(),
        var buttonState: ButtonState = ButtonState(),
        var treeState: TreeState = TreeState(),
    )

    data class PopupMenuState(
        var borderCornerRadius: Int = 5,
        var selectionArc: Int = 5,
    )

    data class ComboBoxState(
        var style: String = "Material",
        var arc: Int = 5,
    )

    data class ButtonState(
        var style: String = "Material",
        var arc: Int = 5,
    )

    data class TreeState(
        var rowHeight: Int = 24,
        var selectionArc: Int = 5,
    )
}
