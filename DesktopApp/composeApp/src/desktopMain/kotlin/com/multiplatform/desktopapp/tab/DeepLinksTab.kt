package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import java.awt.Desktop
import java.net.URI

object DeepLinksTab : Tab {
    private fun readResolve(): Any = DeepLinksTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 2u,
                title = "DeepLinks",
                icon = null
            )
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val buttons = listOf(
            "Compose Web" to "https://cristianmfz.github.io",
            "Compose HTML" to "https://composehtml-js.onrender.com"
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            buttons.forEachIndexed { index, pair ->
                TooltipArea(
                    tooltip = {
                        Surface(
                            modifier = Modifier.shadow(8.dp),
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (index == 0) "ComposeWeb with Kotlin/Wasm"
                                else "ComposeHTML with Kotlin/JS",
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    },
                    delayMillis = 600,
                    tooltipPlacement = TooltipPlacement.ComponentRect(
                        alignment = Alignment.BottomEnd
                    )
                ) {
                    Button(onClick = { openUrl(pair.second) }) {
                        Text(
                            text = pair.first,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }
                if (index == 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    private fun openUrl(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        }
    }

}