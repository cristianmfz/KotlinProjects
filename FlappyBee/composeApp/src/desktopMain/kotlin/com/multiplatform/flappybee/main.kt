package com.multiplatform.flappybee

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.multiplatform.flappybee.di.initializeKoin

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "FlappyBee",
        state = WindowState(
            placement = WindowPlacement.Maximized
        ),
        resizable = true
    ) {
        App()
    }
}