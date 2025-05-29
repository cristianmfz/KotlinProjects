package com.android.borutoapp.ui.screen.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.borutoapp.navigation.Destination
import com.android.borutoapp.ui.common.ListContent

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Destination.Search)
                }
            )
        },
        content = { paddingValues ->
            ListContent(
                heroes = allHeroes,
                navController = navController,
                paddingValues = paddingValues
            )
        }
    )
}