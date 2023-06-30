package com.troy.theme

import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.ui.AntialiasingType
import com.intellij.ide.ui.UISettings
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.ex.util.EditorUIUtil
import com.intellij.openapi.fileEditor.FileEditorManager
import kotlinx.serialization.json.Json
import javax.swing.UIManager

class StartListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {

        // replaceActionButton()

        // replaceStripeButton()

        // replaceAntialiasingType()

        // replaceSunHint()

        ApplicationManager.getApplication().invokeAndWait {
            ThemeExtConfigState.getInstance().applyChange()
        }

        UIManager.addPropertyChangeListener {
            if (it.propertyName == "lookAndFeel") {
                ThemeExtConfigState.getInstance().applyChange()
            }
        }

        // EditorUIUtil.setupAntialiasing()

    }

}
