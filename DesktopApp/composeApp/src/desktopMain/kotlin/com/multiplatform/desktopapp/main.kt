package com.multiplatform.desktopapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import desktopapp.composeapp.generated.resources.*
import desktopapp.composeapp.generated.resources.Res
import desktopapp.composeapp.generated.resources.compose_multiplatform
import desktopapp.composeapp.generated.resources.moon
import desktopapp.composeapp.generated.resources.sun
import org.jetbrains.compose.resources.painterResource

val isWindowVisible = mutableStateOf(false)

fun main() = application {
    val isDarkMode = isSystemInDarkTheme()
    val localMode = remember { mutableStateOf(isDarkMode) }
    val icon = painterResource(Res.drawable.logo)
    val trayState = rememberTrayState()

    if (isTraySupported) {
        Tray(
            state = trayState,
            icon = icon,
            menu = {
                Item(
                    text = "Send Notification",
                    onClick = {
                        trayState.sendNotification(
                            Notification(
                                title = "New notification",
                                message = "Hey there!"
                            )
                        )
                    }
                )
                Item(
                    text = "Exit",
                    onClick = ::exitApplication
                )
            }
        )
    }

    CompositionLocalProvider(LocalThemeMode provides localMode) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DesktopApp",
            //onKeyEvent = { it.key == Key.Delete && it.type == KeyEventType.KeyDown }
        ) {
            App()

            if (isWindowVisible.value) {
                Window(
                    onCloseRequest = { isWindowVisible.value = false },
                    title = "New Window",
                    state = WindowState(width = 300.dp, height = 600.dp),
                    //resizable = false
                ) {
                    SecondWindowContent()
                }
            }
        }
    }
}

@Composable
fun SecondWindowContent() {
    val localMode = LocalThemeMode.current
    val colorScheme = if (localMode.value) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme) {
        Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var showContent by remember { mutableStateOf(false) }
                Row {
                    Button(onClick = { showContent = !showContent }) {
                        Text("Click me!")
                    }
                    IconButton(onClick = { localMode.value = !localMode.value }) {
                        Icon(
                            painter = painterResource(
                                if (localMode.value) Res.drawable.moon else Res.drawable.sun
                            ),
                            contentDescription = "Change theme"
                        )
                    }
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
    }
}