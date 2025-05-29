package tfg.cristian.project

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import tfg.cristian.project.ui.screen.AboutScreen
import tfg.cristian.project.ui.screen.LightFingersScreen
import tfg.cristian.project.ui.screen.LoginScreen
import tfg.cristian.project.ui.screen.MainScreen
import tfg.cristian.project.ui.screen.NinjaBubbleScreen
import tfg.cristian.project.ui.screen.PullTheRopeScreen
import tfg.cristian.project.ui.screen.TicTacToeScreen

@Composable
@Preview
fun App(userRepository: UserRepository) {
    val navController = rememberNavController()
    val username = rememberSaveable {
        mutableStateOf(
            userRepository.getUsername()?.substringBefore("@") ?: "Login"
        )
    }

    NavHost(navController, startDestination = Main) {
        composable<Main> { MainScreen(navController, username.value, userRepository) }
        composable<Login> {
            LoginScreen(navController, userRepository,
                onLoginSuccess = { email ->
                    username.value = email.substringBefore("@")
                    navController.navigateUp()
                },
                onLogoutSuccess = {
                    username.value = "Login"
                    navController.navigateUp()
                }
            )
        }
        composable<TicTacToe> { TicTacToeScreen(navController) }
        composable<PullTheRope> { PullTheRopeScreen(navController) }
        composable<LightFingers> { LightFingersScreen(navController) }
        composable<NinjaBubble> { NinjaBubbleScreen(navController) }
        composable<About> { AboutScreen(navController) }
    }
}

/*@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}*/