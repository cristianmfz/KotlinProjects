package com.multiplatform.composeweb_wasm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composeweb_wasm.composeapp.generated.resources.Res
import composeweb_wasm.composeapp.generated.resources.fire
import composeweb_wasm.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(28.dp))

            Image(
                painterResource(Res.drawable.profile), null,
                modifier = Modifier
                    .height(280.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(8.dp))

            Text("cristianmfz", fontSize = 36.sp, color = Color.White, fontWeight = FontWeight.ExtraBold)

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(LinksProvider.items) {
                    HoverButton(linkItem = it)
                }
            }
        }
    }
}

@Composable
fun HoverButton(linkItem: LinkItem) {
    val uriHandler = LocalUriHandler.current

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor by animateColorAsState(
        if (isHovered) linkItem.backgroundHoverColor else linkItem.backgroundColor
    )
    val textColor by animateColorAsState(
        if (isHovered) linkItem.textHoverColor else linkItem.textColor
    )

    Button(
        onClick = { uriHandler.openUri(linkItem.url) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(60.dp),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        enabled = true,
        shape = CircleShape,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AnimatedVisibility(visible = isHovered) {
                    Image(painterResource(Res.drawable.fire), null)
                    Spacer(Modifier.width(48.dp))
                }
                Icon(
                    painterResource(linkItem.icon), null,
                    modifier = Modifier.size(24.dp).fillMaxSize(1.0F),
                    tint = textColor
                )
                Spacer(Modifier.width(8.dp))

                Text(linkItem.name, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = textColor)

                AnimatedVisibility(visible = isHovered) {
                    Spacer(Modifier.width(48.dp))
                    Image(painterResource(Res.drawable.fire), null)
                }
            }
        }
    )
}

/*@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}*/