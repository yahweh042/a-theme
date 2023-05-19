package com.troy.theme

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon


object IconLookup {

    fun findIcon(
        name: String,
        selected: Boolean = false,
        focused: Boolean = false,
        enabled: Boolean = true,
        editable: Boolean = false,
        pressed: Boolean = false,
    ): Icon {
        var key = name
        if (editable) {
            key += "Editable"
        }
        if (selected) {
            key += "Selected"
        }
        when {
            pressed -> key += "Pressed"
            focused -> key += "Focused"
            !enabled -> key += "Disabled"
        }
        val path = "icons/${key}.svg"
        return IconLoader.findIcon(path, IconLookup::class.java) ?: AllIcons.Actions.Stub
    }

}
