package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object DraggableTab : Tab {
    private fun readResolve(): Any = DraggableTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 7u,
                title = "Draggable",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        DraggableContent()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun DraggableContent() {
        var topBoxOffset by remember { mutableStateOf(Offset(0f, 0f)) }

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(topBoxOffset.x.toInt(), topBoxOffset.y.toInt()) }
                .background(Color.Magenta)
                .pointerInput(Unit) {
                    detectDragGestures(matcher = PointerMatcher.Primary) {
                        topBoxOffset += it
                    }
                }
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Draggable"
            )
        }
    }

}