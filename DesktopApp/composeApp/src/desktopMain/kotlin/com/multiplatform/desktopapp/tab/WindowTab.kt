package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.multiplatform.desktopapp.isWindowVisible

object WindowTab : Tab {
    private fun readResolve(): Any = WindowTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 1u,
                title = "Window",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { isWindowVisible.value = true }) {
                Text(
                    text = "Open Window",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }

}