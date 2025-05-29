package com.android.firebasesongs

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.firebasesongs.ui.theme.FirebaseSongsTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()

        // Verificar si hay un usuario logueado antes de mostrar la UI inicial
        val startDestination = if (auth.currentUser != null) Home else Main

        setContent {
            EnableTransparentStatusBar()
            navHostController = rememberNavController()
            FirebaseSongsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationWrapper(navHostController, auth, startDestination)
                }
            }
        }
    }

    /*override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Navegar a home si el usuario ya está logueado
            Log.i("Cristian", "Estoy logueado")
            // Desloguear porque en onStart, navHostController aún no ha sido inicializado
            auth.signOut()
        }
    }*/
}

@Composable
private fun EnableTransparentStatusBar() {
    val view = LocalView.current
    val darkMode = true //isSystemInDarkTheme()
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        //window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkMode
    }
}