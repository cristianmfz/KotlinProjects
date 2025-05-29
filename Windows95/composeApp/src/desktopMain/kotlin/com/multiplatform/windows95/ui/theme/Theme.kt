package com.multiplatform.windows95.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = windowsBlue,
    onPrimary = Color.White,
    primaryContainer = tertiaryBlack,
    onPrimaryContainer = Color.White,
    secondary = windowsBarTextBackground,
    onSecondary = Color.Black,
    background = Color(0xFF1E1E1E),
    onBackground = Color.White,
    surface = Color(0xFF2A2A2A),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black,
    outline = Color.Gray
)

private val LightColorScheme = lightColorScheme(
    primary = windowsBlue,
    onPrimary = Color.White,
    primaryContainer = backgroundComponent,
    onPrimaryContainer = tertiaryBlack,
    secondary = windowsBarTextBackground,
    onSecondary = Color.White,
    background = background,
    onBackground = Color.Black,
    surface = backgroundComponent,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White,
    outline = Color.DarkGray
)

@Composable
fun Windows95Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Windows95Typography(),
        content = content
    )
}