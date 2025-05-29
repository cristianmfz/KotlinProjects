package com.multiplatform.kmpmovies.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.multiplatform.kmpmovies.ui.screens.detail.DetailScreen
import com.multiplatform.kmpmovies.ui.screens.home.HomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(Detail(movie.id))
                }
            )
        }
        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            DetailScreen(
                vm = koinViewModel(parameters = { parametersOf(detail.movieId) }),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/*@Composable
private fun rememberMoviesRepository(moviesDao: MoviesDao): MoviesRepository = remember {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", BuildConfig.API_KEY)
            }
        }
    }
    MoviesRepository(MoviesService(client), moviesDao)
}*/