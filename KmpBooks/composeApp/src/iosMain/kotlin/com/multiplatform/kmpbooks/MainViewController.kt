package com.multiplatform.kmpbooks

import androidx.compose.ui.window.ComposeUIViewController
import com.multiplatform.kmpbooks.di.initializeKoin

fun MainViewController() = ComposeUIViewController(configure = { initializeKoin() }) {
    App()
}