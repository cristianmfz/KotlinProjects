package com.android.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.borutoapp.ui.screen.detail.DetailScreen
import com.android.borutoapp.ui.screen.home.HomeScreen
import com.android.borutoapp.ui.screen.search.SearchScreen
import com.android.borutoapp.ui.screen.splash.SplashScreen
import com.android.borutoapp.ui.screen.welcome.WelcomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.Splash
    ) {
        composable<Destination.Splash> {
            SplashScreen(navController = navController)
        }
        composable<Destination.Welcome> {
            WelcomeScreen(navController = navController)
        }
        composable<Destination.Home> {
            HomeScreen(navController = navController)
        }
        composable<Destination.Detail> {
            DetailScreen(navController = navController)
        }
        composable<Destination.Search> {
            SearchScreen(navController = navController)
        }
    }
}