package com.android.back4appchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.android.back4appchat.navigation.Destination
import com.android.back4appchat.navigation.SetupNavGraph
import com.android.back4appchat.ui.theme.Back4appChatTheme
import com.parse.ParseUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Back4appChatTheme {
                SetupNavGraph(
                    navController = rememberNavController(),
                    startDestination = if (ParseUser.getCurrentUser() != null) Destination.Home
                    else Destination.Auth
                )
            }
        }
    }
}