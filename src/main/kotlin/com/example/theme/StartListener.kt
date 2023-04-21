package com.example.theme

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.application.ApplicationManager

class StartListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
//        UIManager.setLookAndFeel(MaterialLaf())
//        LafManager.getInstance().updateUI()
//
//        val uiSettings = UISettings.getInstance()
//        val overrideLafFonts = uiSettings.overrideLafFonts
//        if (overrideLafFonts) {
//
//        } else if (SystemInfo.isMac) {
//            val lookAndFeelDefaults = UIManager.getLookAndFeelDefaults()
//            LafManagerImpl.installMacOSXFonts(lookAndFeelDefaults)
//        }
//
//

        ApplicationManager.getApplication().invokeAndWait {
            ThemeExtConfigState.getInstance().applyChange()
        }

    }

}
