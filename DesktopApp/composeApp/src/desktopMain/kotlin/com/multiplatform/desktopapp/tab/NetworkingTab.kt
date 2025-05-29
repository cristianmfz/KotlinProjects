package com.multiplatform.desktopapp.tab

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.multiplatform.desktopapp.api.ApiService
import kotlinx.coroutines.launch

object NetworkingTab : Tab {
    private fun readResolve(): Any = NetworkingTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 3u,
                title = "Networking",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        var apiResponse by remember { mutableStateOf("Waiting...") }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    scope.launch {
                        apiResponse = ApiService().fetchData()
                    }
                }
            ) {
                Text(
                    text = "Get Response",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = apiResponse,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}