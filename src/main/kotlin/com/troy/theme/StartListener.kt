package com.troy.theme

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.application.ApplicationManager
import javax.swing.UIManager

class StartListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {

        replaceActionButton()

        // replaceStripeButton()

        ApplicationManager.getApplication().invokeAndWait {
            ThemeExtConfigState.getInstance().applyChange()
        }

        UIManager.addPropertyChangeListener {
            if (it.propertyName == "lookAndFeel") {
                ThemeExtConfigState.getInstance().applyChange()
            }
        }

    }

}
