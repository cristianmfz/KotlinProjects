package com.multiplatform.rickverseapp.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.multiplatform.rickverseapp.navigation.Destination
import com.multiplatform.rickverseapp.navigation.Destination.CharacterDetail
import com.multiplatform.rickverseapp.ui.home.tabs.characters.CharactersScreen
import com.multiplatform.rickverseapp.ui.home.tabs.episodes.EpisodesScreen
import kotlinx.serialization.json.Json

@Composable
fun NavigationBottomWrapper(navController: NavHostController, mainNavController: NavHostController){

    NavHost(navController = navController, startDestination = Destination.Episodes){

        composable<Destination.Episodes>{
            EpisodesScreen()
        }

        composable<Destination.Characters>{
            CharactersScreen(
                navigateToDetail = { characterModel ->
                    val encode: String = Json.encodeToString(characterModel)
                    mainNavController.navigate(CharacterDetail(encode))
                }
            )
        }
    }

}