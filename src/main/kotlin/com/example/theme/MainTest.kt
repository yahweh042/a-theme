package com.example.theme

import com.intellij.ui.dsl.builder.panel
import java.awt.Frame

fun main(args: Array<String>) {
    val panel = panel {
        row {
            button("Hello") {

            }
        }
    }
    val frame = Frame()

    frame.add(panel)
    frame.pack()
    frame.isVisible = true
}
