package com.troy.theme

import com.intellij.openapi.actionSystem.ex.ActionButtonLook
import javassist.ClassPool
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import java.lang.reflect.Modifier

fun replaceStripeButton() {

    val cp = ClassPool(true)
    cp.importPackage("com.intellij.openapi.actionSystem.ex.ActionButtonLook")
    val ct = cp.get("com.intellij.openapi.actionSystem.impl.ActionToolbarImpl")
    val cm = ct.getDeclaredMethod("setCustomButtonLook")
    cm.setBody("{\nmyCustomButtonLook = ActionButtonLook.SYSTEM_LOOK;\nrepaint();\n}")
    ct.toClass()

}

fun replaceActionButton() {

    // try {
    //     val privateLookupIn = MethodHandles.privateLookupIn(Field::class.java, MethodHandles.lookup())
    //     val findVarHandle = privateLookupIn.findVarHandle(Field::class.java, "modifiers", Integer.TYPE)
    //     val field = ActionButtonLook::class.java.getDeclaredField("SYSTEM_LOOK")
    //     field.isAccessible = true
    //     findVarHandle.set(field, field.modifiers and Modifier.FINAL.inv())
    //     field.set(null, AActionButtonLook())
    //     findVarHandle.set(field, field.modifiers and Modifier.FINAL)
    //     field.isAccessible = false
    // } catch (e: IllegalAccessException) {
    //     e.printStackTrace()
    // } catch (e: NoSuchFieldException) {
    //     e.printStackTrace()
    // } catch (e: SecurityException) {
    //     e.printStackTrace()
    // }

}
