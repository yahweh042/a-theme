package com.troy.theme

import com.intellij.openapi.actionSystem.ex.ActionButtonLook
import com.intellij.openapi.editor.PlatformEditorBundle
import javassist.ClassPool
import javassist.CtClass
import javassist.CtNewConstructor
import java.awt.RenderingHints
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field
import java.lang.reflect.Modifier


fun replaceAntialiasingType() {
    val pool = ClassPool.getDefault()
    pool.importPackage("com.intellij.openapi.editor.PlatformEditorBundle")
    pool.importPackage("java.awt.RenderingHints")
    val ctClass = pool["com.intellij.ide.ui.AntialiasingType"]
    val field = ctClass.getDeclaredField("OFF")
    field.modifiers = field.modifiers and Modifier.FINAL.inv() // 移除 final 修饰符


    val stringCtClass = pool.get("java.lang.String")
    val objectCtClass = pool.get("java.lang.Object")
    val params = arrayOf(stringCtClass, stringCtClass, objectCtClass, CtClass.booleanType)
    val constructor = CtNewConstructor.make(params, null, CtNewConstructor.PASS_NONE, null, null, ctClass)
    constructor.setBody("{ $0.mySerializationName = $1; $0.myPresentableName = $2; $0.myHint = $3; $0.isEnabled = $4; }")
    ctClass.addConstructor(constructor)

    ctClass.toClass()

    replace0()
}

fun replace0() {

    val pool = ClassPool.getDefault()
    pool.importPackage("com.intellij.openapi.editor.PlatformEditorBundle")
    pool.importPackage("java.awt.RenderingHints")
    val ctClass = pool["com.intellij.ide.ui.AntialiasingType"]
    val method = ctClass.getDeclaredMethod("values")
    method.setBody(
        "{ return new com.intellij.ide.ui.AntialiasingType[] { SUBPIXEL, GREYSCALE, new com.intellij.ide.ui.AntialiasingType(\"OFF\", \"" + PlatformEditorBundle.message(
            "settings.editor.antialiasing.no.antialiasing"
        ) + "\", RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, true) }; }"
    )
    ctClass.toClass()
}

fun replaceStripeButton() {

    val cp = ClassPool(true)
    cp.importPackage("com.intellij.openapi.actionSystem.ex.ActionButtonLook")
    val ct = cp.get("com.intellij.openapi.actionSystem.impl.ActionToolbarImpl")
    val cm = ct.getDeclaredMethod("setCustomButtonLook")
    cm.setBody("{\nmyCustomButtonLook = ActionButtonLook.SYSTEM_LOOK;\nrepaint();\n}")
    ct.toClass()

}

fun replaceSunHint() {

    try {
        val privateLookupIn = MethodHandles.privateLookupIn(Field::class.java, MethodHandles.lookup())
        val findVarHandle = privateLookupIn.findVarHandle(Field::class.java, "modifiers", Integer.TYPE)
        val field = RenderingHints::class.java.getDeclaredField("VALUE_TEXT_ANTIALIAS_ON")
        field.isAccessible = true
        findVarHandle.set(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB)
        findVarHandle.set(field, field.modifiers and Modifier.FINAL)
        field.isAccessible = false
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: SecurityException) {
        e.printStackTrace()
    }

}

fun replaceActionButton() {

    try {
        val privateLookupIn = MethodHandles.privateLookupIn(Field::class.java, MethodHandles.lookup())
        val findVarHandle = privateLookupIn.findVarHandle(Field::class.java, "modifiers", Integer.TYPE)
        val field = ActionButtonLook::class.java.getDeclaredField("SYSTEM_LOOK")
        field.isAccessible = true
        findVarHandle.set(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, AActionButtonLook())
        findVarHandle.set(field, field.modifiers and Modifier.FINAL)
        field.isAccessible = false
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: SecurityException) {
        e.printStackTrace()
    }

}

fun replaceActionMenu() {
    val cp = ClassPool(true)
    cp.importPackage("com.troy.theme.AMenuUI")
    cp.importPackage("com.intellij.util.ui.UIUtil")
    cp.importPackage("javax.swing.JPopupMenu")
    val ct = cp.get("com.intellij.openapi.actionSystem.impl.ActionMenu")
    val cm = ct.getDeclaredMethod("updateUI")
    cm.setBody(
        "{\nsetUI(AMenuUI.createUI(this));\n" +
                "    setFont(UIUtil.getMenuFont());\n" +
                "\n" +
                "    JPopupMenu popupMenu = getPopupMenu();\n" +
                "    if (popupMenu != null) {\n" +
                "      popupMenu.updateUI();\n" +
                "    }\n" +
                "}"
    )
    ct.toClass()

}
