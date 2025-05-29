package com.multiplatform.flappybee

import androidx.compose.ui.window.ComposeUIViewController
import com.multiplatform.flappybee.di.initializeKoin

fun MainViewController() = ComposeUIViewController(configure = { initializeKoin() }) {
    App()
}