package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object KeyboardTab : Tab {
    private fun readResolve(): Any = KeyboardTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 5u,
                title = "Keyboard",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        KeyEventContent()
    }

    @Composable
    fun KeyEventContent() {
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = text1,
                onValueChange = { text1 = it },
                modifier = Modifier.onPreviewKeyEvent {
                    if (it.key == Key.Delete && it.type == KeyEventType.KeyDown) {
                        text1 = ""
                        true
                    }
                    else false
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            ContextMenuDataProvider(
                items = {
                    listOf(
                        ContextMenuItem(label = "Custom Action") {
                            println("Custom Action Clicked")
                        }
                    )
                }
            ) {
                TextField(
                    value = text2,
                    onValueChange = { text2 = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                SelectionContainer {
                    Text(
                        text = "Hello World!",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}