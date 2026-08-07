package com.vahitkeskin.kaprekarconstant

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.kaprekar.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "KaprekarConstant",
    ) {
        App()
    }
}