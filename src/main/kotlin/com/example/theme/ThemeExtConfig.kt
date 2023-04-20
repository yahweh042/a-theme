package com.example.theme

import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.ui.scale.JBUIScale
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Property
import javax.swing.UIManager

@State(name = "ThemeExtConfig", storages = [Storage("theme_ext_config.xml")])
class ThemeExtConfig : PersistentStateComponent<ThemeExtConfig> {

    @Property
    var listRowHeight: Int = 24

    @Property
    var treeRowHeight: Int = 24

    override fun getState(): ThemeExtConfig = this

    override fun loadState(state: ThemeExtConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun applyChange() {
        val defaults = UIManager.getDefaults()
        defaults["List.rowHeight"] = JBUIScale.scale(this.listRowHeight)
        defaults["Tree.rowHeight"] = JBUIScale.scale(this.treeRowHeight)
        LafManager.getInstance().updateUI()
    }

    companion object {

        @JvmStatic
        fun getInstance(): ThemeExtConfig = ApplicationManager.getApplication().getService(ThemeExtConfig::class.java)

    }
}
