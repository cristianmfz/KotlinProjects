package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object MouseTab : Tab {
    private fun readResolve(): Any = MouseTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 6u,
                title = "Mouse",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        ClickEventContent()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ClickEventContent() {
        var text by remember { mutableStateOf("Click the box!") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(0.2f)
                    .combinedClickable(
                        onClick = {
                            text = "Single Click!"
                        },
                        onDoubleClick = {
                            text = "Double Click!"
                        },
                        onLongClick = {
                            text = "Long Click!"
                        }
                    )
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = text,
                fontSize = 40.sp
            )
        }
    }

}