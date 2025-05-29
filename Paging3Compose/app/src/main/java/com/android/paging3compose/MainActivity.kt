package com.android.paging3compose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.android.paging3compose.ui.DragonListScreen
import com.android.paging3compose.ui.theme.Paging3ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnableTransparentStatusBar()
            Paging3ComposeTheme {
                DragonListScreen()
            }
        }
    }
}

@Composable
private fun EnableTransparentStatusBar() {
    val view = LocalView.current
    val darkMode = true //isSystemInDarkTheme()
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        //window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkMode
    }
}