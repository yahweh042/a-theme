package com.example.theme

import javassist.ClassPool

fun replaceStripeButton() {

    val cp = ClassPool(true)
    cp.importPackage("com.intellij.openapi.actionSystem.ex.ActionButtonLook")
    val ct = cp.get("com.intellij.openapi.actionSystem.impl.ActionButton")
    val cm = ct.getDeclaredMethod("setLook")
    cm.setBody("{\nmyLook = ActionButtonLook.SYSTEM_LOOK;\nrepaint();\n}")
    ct.toClass()


}
