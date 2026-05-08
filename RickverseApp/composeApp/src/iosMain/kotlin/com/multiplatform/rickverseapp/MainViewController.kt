package com.multiplatform.rickverseapp

import androidx.compose.ui.window.ComposeUIViewController
import com.multiplatform.rickverseapp.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }