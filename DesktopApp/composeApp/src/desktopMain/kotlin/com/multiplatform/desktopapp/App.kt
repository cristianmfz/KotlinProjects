package com.multiplatform.desktopapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.multiplatform.desktopapp.tab.*
import desktopapp.composeapp.generated.resources.Res
import desktopapp.composeapp.generated.resources.moon
import desktopapp.composeapp.generated.resources.sun
import org.jetbrains.compose.resources.painterResource

val LocalThemeMode = compositionLocalOf { mutableStateOf(true) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val localMode = LocalThemeMode.current
    val colorScheme = if (localMode.value) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme) {
        TabNavigator(HomeTab) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        actions = {
                            TabNavigationItem(HomeTab)
                            TabNavigationItem(WindowTab)
                            TabNavigationItem(DeepLinksTab)
                            TabNavigationItem(NetworkingTab)
                            TabNavigationItem(ScrollbarsTab)
                            TabNavigationItem(KeyboardTab)
                            TabNavigationItem(MouseTab)
                            TabNavigationItem(DraggableTab)

                            IconButton(onClick = { localMode.value = !localMode.value }) {
                                Icon(
                                    painter = painterResource(
                                        if (localMode.value) Res.drawable.moon else Res.drawable.sun
                                    ),
                                    contentDescription = "Change theme"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.inverseSurface,
                            actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                        ),
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CurrentTab()
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        label = {
            Text(
                text = tab.options.title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
        },
        icon = {}
    )
}