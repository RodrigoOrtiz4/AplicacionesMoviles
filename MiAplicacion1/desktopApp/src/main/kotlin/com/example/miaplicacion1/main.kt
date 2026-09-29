package com.example.miaplicacion1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MiAplicacion1",
    ) {
        App()
    }
}