package com.multiplatform.rickverseapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.multiplatform.rickverseapp.ui.common.theme.BackgroundPrimaryColor
import com.multiplatform.rickverseapp.ui.common.theme.BackgroundSecondaryColor
import com.multiplatform.rickverseapp.ui.common.theme.BackgroundTertiaryColor
import com.multiplatform.rickverseapp.ui.common.theme.DefaultTextColor
import com.multiplatform.rickverseapp.ui.common.theme.Green
import com.multiplatform.rickverseapp.navigation.bottomnavigation.BottomBarItem
import com.multiplatform.rickverseapp.navigation.bottomnavigation.BottomBarItem.*
import com.multiplatform.rickverseapp.navigation.bottomnavigation.NavigationBottomWrapper
import org.jetbrains.compose.resources.painterResource
import rickverseapp.composeapp.generated.resources.Res
import rickverseapp.composeapp.generated.resources.ricktoolbar

@Composable
fun HomeScreen(mainNavController: NavHostController) {
    val items = listOf(Episodes(), Characters())
    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigation(items, navController) },
        topBar = { TopBar() }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavigationBottomWrapper(navController, mainNavController)
        }
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundPrimaryColor)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ricktoolbar),
            contentDescription = null,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun BottomNavigation(items: List<BottomBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = BackgroundSecondaryColor, contentColor = Green) {
        items.forEach { item ->
            NavigationBarItem(colors = NavigationBarItemDefaults.colors(
                indicatorColor = Green,
                selectedIconColor = BackgroundTertiaryColor,
                unselectedIconColor = Green
            ),
                icon = item.icon,
                label = { Text(item.title, color = DefaultTextColor) },
                onClick = {
                    navController.navigate(item.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.destination::class) } == true,
                alwaysShowLabel = false)
        }
    }
}