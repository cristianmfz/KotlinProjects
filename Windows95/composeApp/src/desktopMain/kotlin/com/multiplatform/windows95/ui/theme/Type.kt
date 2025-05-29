package com.multiplatform.windows95.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import windows95.composeapp.generated.resources.Res
import windows95.composeapp.generated.resources.W95FA

@Composable
fun Windows95Typography(): Typography {
    val win95FontFamily = FontFamily(Font(Res.font.W95FA))

    fun win95Style(size: Int) = TextStyle(
        fontFamily = win95FontFamily,
        fontSize = size.sp
    )

    return Typography(
        displayLarge = win95Style(57),
        displayMedium = win95Style(45),
        displaySmall = win95Style(36),
        headlineLarge = win95Style(32),
        headlineMedium = win95Style(28),
        headlineSmall = win95Style(24),
        titleLarge = win95Style(22),
        titleMedium = win95Style(16),
        titleSmall = win95Style(14),
        bodyLarge = win95Style(16),
        bodyMedium = win95Style(14),
        bodySmall = win95Style(12),
        labelLarge = win95Style(14),
        labelMedium = win95Style(12),
        labelSmall = win95Style(11)
    )
}