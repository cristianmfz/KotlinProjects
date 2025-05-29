package com.multiplatform.desktopapp.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.multiplatform.desktopapp.screen.HomeScreen

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    @get:Composable
    override val options: TabOptions
        get() = remember {
            TabOptions(
                index = 0u,
                title = "Home",
                icon = null
            )
        }

    @Composable
    override fun Content() {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

}