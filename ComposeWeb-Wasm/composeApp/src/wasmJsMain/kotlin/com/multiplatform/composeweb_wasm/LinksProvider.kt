package com.multiplatform.composeweb_wasm

import androidx.compose.ui.graphics.Color
import com.multiplatform.composeweb_wasm.theme.TwitchColor
import composeweb_wasm.composeapp.generated.resources.*
import composeweb_wasm.composeapp.generated.resources.Res

object LinksProvider {
    val items = listOf(
        LinkItem(
            name = "TikTok",
            url = "https://www.tiktok.com/@cristianmfz",
            backgroundColor = Color.Black,
            icon = Res.drawable.tiktok
        ),
        LinkItem(
            name = "Youtube",
            url = "https://www.youtube.com/@cristianmfz",
            backgroundColor = Color.Red,
            icon = Res.drawable.youtube
        ),
        LinkItem(
            name = "Instagram",
            url = "https://www.instagram.com/cristianmfz_",
            backgroundColor = Color.Magenta,
            icon = Res.drawable.instagram
        ),
        LinkItem(
            name = "Twitch",
            url = "https://www.twitch.tv/cristianmfz",
            backgroundColor = TwitchColor,
            icon = Res.drawable.twitch
        ),
        LinkItem(
            name = "X",
            url = "https://x.com/cristianmfz",
            backgroundColor = Color.LightGray,
            backgroundHoverColor = Color.Black,
            textColor = Color.Black,
            icon = Res.drawable.x
        ),
        LinkItem(
            name = "GitHub",
            url = "https://github.com/cristianmfz",
            backgroundColor = Color.DarkGray,
            icon = Res.drawable.github
        )
    )
}