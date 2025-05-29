package com.android.back4appchat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.back4appchat.ui.screen.auth.AuthScreen
import com.android.back4appchat.ui.screen.chat.ChatScreen
import com.android.back4appchat.ui.screen.create_room.CreateRoomScreen
import com.android.back4appchat.ui.screen.home.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Auth> {
            AuthScreen(
                onAuthenticated = {
                    navController.popBackStack()
                    navController.navigate(Destination.Home)
                }
            )
        }
        composable<Destination.Home> {
            HomeScreen(
                onLogout = {
                    navController.popBackStack()
                    navController.navigate(Destination.Auth)
                },
                onCreateRoom = {
                    navController.navigate(Destination.CreateRoom)
                },
                onChatRoomSelect = {
                    navController.navigate(Destination.Chat(id = it))
                }
            )
        }
        composable<Destination.CreateRoom> {
            CreateRoomScreen(
                openChatScreen = {
                    navController.popBackStack()
                    navController.navigate(Destination.Chat(it))
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
        composable<Destination.Chat> {
            ChatScreen(onBackClick = { navController.navigateUp() })
        }
    }
}