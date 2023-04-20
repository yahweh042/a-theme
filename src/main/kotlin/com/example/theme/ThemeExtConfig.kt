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
import com.intellij.util.xmlb.annotations.Property
import javax.swing.UIDefaults
import javax.swing.UIManager

@State(name = "ThemeExtConfig", storages = [Storage("theme_ext_config.xml")])
class ThemeExtConfig : PersistentStateComponent<ThemeExtConfig> {

    @Property
    var listRowHeight: Int = 24

    @Property
    var treeRowHeight: Int = 24

    @Property
    var buttonStyle: String = "Material"

    @Property
    var fieldStyle: String = "Material"

    @Property
    var comboBoxStyle: String = "Material"

    override fun getState(): ThemeExtConfig = this

    override fun loadState(state: ThemeExtConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun applyChange() {
        val defaults = UIManager.getDefaults()
        defaults["List.rowHeight"] = JBUIScale.scale(this.listRowHeight)
        defaults["Tree.rowHeight"] = JBUIScale.scale(this.treeRowHeight)

        applyButtonStyle(defaults)
        applyComboBoxStyle(defaults)

        LafManager.getInstance().updateUI()
    }

    private fun applyButtonStyle(defaults: UIDefaults) {
        when (this.buttonStyle) {
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
        println(this.comboBoxStyle)
        when (this.comboBoxStyle) {
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
        fun getInstance(): ThemeExtConfig = ApplicationManager.getApplication().getService(ThemeExtConfig::class.java)

    }
}
