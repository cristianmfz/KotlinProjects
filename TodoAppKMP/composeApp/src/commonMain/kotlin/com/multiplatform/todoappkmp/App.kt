package com.multiplatform.todoappkmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.multiplatform.todoappkmp.navigation.SetupNavGraph
import com.multiplatform.todoappkmp.ui.theme.darkColors
import com.multiplatform.todoappkmp.ui.theme.lightColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val colors by mutableStateOf(
        if (isSystemInDarkTheme()) darkColors else lightColors
    )

    MaterialTheme(colorScheme = colors) {
        val navController = rememberNavController()
        SetupNavGraph(navController = navController)
    }
}