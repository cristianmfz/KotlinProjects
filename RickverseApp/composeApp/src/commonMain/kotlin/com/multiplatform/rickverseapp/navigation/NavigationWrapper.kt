package com.multiplatform.rickverseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.multiplatform.rickverseapp.domain.model.CharacterModel
import com.multiplatform.rickverseapp.ui.detail.CharacterDetailScreen
import com.multiplatform.rickverseapp.ui.home.HomeScreen
import kotlinx.serialization.json.Json

@Composable
fun NavigationWrapper() {
    val mainNavController = rememberNavController()

    NavHost(navController = mainNavController, startDestination = Destination.Home) {
        composable<Destination.Home> {
            HomeScreen(mainNavController)
        }

        composable<Destination.CharacterDetail> { navBackStackEntry ->
            val characterDetail: Destination.CharacterDetail =
                navBackStackEntry.toRoute<Destination.CharacterDetail>()
            val characterModel: CharacterModel =
                Json.decodeFromString<CharacterModel>(characterDetail.characterModel)
            CharacterDetailScreen(
                characterModel = characterModel,
                onBackPressed = { mainNavController.popBackStack() })
        }
    }
}