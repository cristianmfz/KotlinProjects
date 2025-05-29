package com.multiplatform.composeweb_wasm

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

data class LinkItem(
    val name: String,
    val url: String,
    val backgroundColor: Color,
    val backgroundHoverColor: Color = Color.White,
    val icon: DrawableResource,
    val textColor: Color = Color.White,
    val textHoverColor: Color = backgroundColor
)