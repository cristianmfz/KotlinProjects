package com.android.firebasesongs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.firebasesongs.ui.home.HomeScreen
import com.android.firebasesongs.ui.login.LoginScreen
import com.android.firebasesongs.ui.main.MainScreen
import com.android.firebasesongs.ui.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    startDestination: Any
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable<Main> {
            MainScreen(
                navigateToLogin = { navHostController.navigate(Login) },
                navigateToSignup = { navHostController.navigate(Signup) }
            )
        }
        composable<Login> {
            LoginScreen(auth, navHostController)
        }
        composable<Signup> {
            SignupScreen(auth, navHostController)
        }
        composable<Home> {
            HomeScreen(navHostController)
        }
    }
}