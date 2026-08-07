package com.vahitkeskin.kaprekarconstant

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KaprekarConstant",
    ) {
        App()
    }
}