package com.multiplatform.windows95

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.multiplatform.windows95.ui.screen.splash.SplashScreen
import com.multiplatform.windows95.ui.screen.windows95.Windows95Screen
import com.multiplatform.windows95.ui.theme.Windows95Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val isDarkMode = remember { mutableStateOf(false) }

    Windows95Theme(darkTheme = isDarkMode.value) {
        var initializing by remember { mutableStateOf(true) }

        Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            if (initializing) {
                SplashScreen { initializing = false }
            } else {
                Windows95Screen(isDarkMode)
            }
        }
    }
}