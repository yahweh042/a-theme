package com.example.theme

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.actionSystem.ex.ActionButtonLook
import com.intellij.openapi.application.ApplicationManager
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class StartListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        val privateLookupIn = MethodHandles.privateLookupIn(Field::class.java, MethodHandles.lookup())
        val findVarHandle = privateLookupIn.findVarHandle(Field::class.java, "modifiers", Integer.TYPE)


        val field = ActionButtonLook::class.java.getDeclaredField("SYSTEM_LOOK")
        field.isAccessible = true
        findVarHandle.set(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, AActionButtonLook())
        findVarHandle.set(field, field.modifiers and Modifier.FINAL)
        field.isAccessible = false


        replaceStripeButton()

        ApplicationManager.getApplication().invokeAndWait {
            ThemeExtConfigState.getInstance().applyChange()
        }

    }

}
