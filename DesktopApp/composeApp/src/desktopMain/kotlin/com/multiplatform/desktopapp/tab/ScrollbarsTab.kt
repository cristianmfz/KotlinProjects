package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object ScrollbarsTab : Tab {
    private fun readResolve(): Any = ScrollbarsTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 4u,
                title = "Scrollbars",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        ScrollableLazyList()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun ScrollableLazyList() {
        val lazyListState = rememberLazyListState()
        val horizontalScroll = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(horizontalScroll)
                    .padding(end = 12.dp, bottom = 12.dp),
                state = lazyListState
            ) {
                items(1000) { num ->
                    var hovered by remember { mutableStateOf(false) }
                    val animatedColor = if (hovered) Color.Magenta else MaterialTheme.colorScheme.surface

                    Text(
                        modifier = Modifier
                            .background(color = animatedColor)
                            .padding(all = 12.dp)
                            .onPointerEvent(PointerEventType.Enter) { hovered = true }
                            .onPointerEvent(PointerEventType.Exit) { hovered = false },
                        text = "This is the item number: #$num"
                    )
                }
            }
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(lazyListState)
            )
            HorizontalScrollbar(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                adapter = rememberScrollbarAdapter(horizontalScroll)
            )
        }
    }

    /*@OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun ScrollableList() {
        val verticalScroll = rememberScrollState()
        val horizontalScroll = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScroll)
                    .horizontalScroll(horizontalScroll)
                    .padding(end = 12.dp, bottom = 12.dp)
            ) {
                for (item in 0..30) {
                    var hovered by remember { mutableStateOf(false) }
                    val animatedColor = if (hovered) Color.Cyan else MaterialTheme.colorScheme.surface

                    Text(
                        modifier = Modifier
                            .background(color = animatedColor)
                            .padding(all = 12.dp)
                            .onPointerEvent(PointerEventType.Enter) { hovered = true }
                            .onPointerEvent(PointerEventType.Exit) { hovered = false },
                        text = "This is the item number: #$item"
                    )
                }
            }
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(verticalScroll)
            )
            HorizontalScrollbar(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                adapter = rememberScrollbarAdapter(horizontalScroll)
            )
        }
    }*/

}