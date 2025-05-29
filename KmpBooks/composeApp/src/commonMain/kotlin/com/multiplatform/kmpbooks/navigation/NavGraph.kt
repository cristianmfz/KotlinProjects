package com.multiplatform.kmpbooks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.multiplatform.kmpbooks.ui.screen.detail.DetailScreen
import com.multiplatform.kmpbooks.ui.screen.home.HomeScreen
import com.multiplatform.kmpbooks.ui.screen.manage.ManageScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onBookSelect = { navController.navigate(Detail(bookId = it)) },
                onCreateClick = { navController.navigate(Manage()) }
            )
        }
        composable<Manage> {
            val id = it.arguments?.getInt(BOOK_ID) ?: -1
            ManageScreen(id = id, onBackClick = { navController.navigateUp() })
        }
        composable<Detail> {
            val id = it.arguments?.getInt(BOOK_ID) ?: 0
            DetailScreen(
                onEditClick = { navController.navigate(Manage(bookId = id)) },
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}