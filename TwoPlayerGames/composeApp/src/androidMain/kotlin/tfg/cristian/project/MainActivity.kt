package tfg.cristian.project

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private val userRepository by lazy { AndroidUserRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnableTransparentStatusBar()
            App(userRepository)
        }
        // Inicializa y reproduce la música de fondo
        mediaPlayer = MediaPlayer.create(this, R.raw.lofi_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        // Pausa la música de fondo cuando la aplicación pasa a segundo plano
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        // Reanuda la música de fondo cuando la aplicación vuelve al primer plano
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Libera los recursos del MediaPlayer
    }
}

@Composable
private fun EnableTransparentStatusBar() {
    val view = LocalView.current
    val darkMode = isSystemInDarkTheme()
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        //window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkMode
    }
}

@Preview(showBackground = true)
@Composable
fun AppAndroidPreview() {
    App(AndroidUserRepository(LocalContext.current))
    //MainScreen(rememberNavController(), userRepository.getUsername()?.substringBefore("@") ?: "Login", userRepository)
    //LoginScreen(rememberNavController(), AndroidUserRepository(LocalContext.current), onLoginSuccess = {}, onLogoutSuccess = {})
    //TicTacToeScreen(rememberNavController())
    //PullTheRopeScreen(rememberNavController())
    //LightFingersScreen(rememberNavController())
    //AboutScreen(rememberNavController())
}