package com.example.theme

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonPainter
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.ide.ui.laf.darcula.ui.DarculaComboBoxUI
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
        XmlSerializerUtil.copyBean(state, this)
    }

    fun setListRowHeight(listRowHeight: Int) {
        themeExtConfig.listRowHeight = listRowHeight
    }

    fun getListRowHeight() = themeExtConfig.listRowHeight

    fun applyChange() {
        val defaults = UIManager.getDefaults()
        defaults["List.rowHeight"] = JBUIScale.scale(state.listRowHeight)
        defaults["Tree.rowHeight"] = JBUIScale.scale(state.treeRowHeight)

        applyButtonStyle(defaults)
        applyComboBoxStyle(defaults)

        LafManager.getInstance().updateUI()
    }

    private fun applyButtonStyle(defaults: UIDefaults) {
        when (state.buttonStyle) {
            "Material" -> {
                defaults["ButtonUI"] = MaterialButtonUI::class.java.name
                defaults[MaterialButtonUI::class.java.name] = MaterialButtonUI::class.java
                defaults["Button.border"] = MaterialButtonBorder()
            }

            else -> {
                defaults["ButtonUI"] = DarculaButtonUI::class.java.name
                defaults[DarculaButtonUI::class.java.name] = DarculaButtonUI::class.java
                defaults["Button.border"] = DarculaButtonPainter()
            }
        }
    }

    private fun applyComboBoxStyle(defaults: UIDefaults) {
        println(state.comboBoxStyle)
        when (state.comboBoxStyle) {
            "Material" -> {
                defaults["ComboBoxUI"] = MaterialComboBoxUI::class.java.name
                defaults[MaterialComboBoxUI::class.java.name] = MaterialComboBoxUI::class.java
            }

            else -> {
                defaults["ComboBoxUI"] = DarculaComboBoxUI::class.java.name
                defaults[DarculaComboBoxUI::class.java.name] = DarculaComboBoxUI::class.java
            }
        }
    }

    companion object {

        @JvmStatic
        fun getInstance(): ThemeExtConfigState = ApplicationManager.getApplication().getService(ThemeExtConfigState::class.java)

    }

    data class ThemeExtConfig(
        var listRowHeight: Int = 24,
        var treeRowHeight: Int = 24,
        var buttonStyle: String = "Material",
        var fieldStyle: String = "Material",
        var comboBoxStyle: String = "Material",
    )
}
