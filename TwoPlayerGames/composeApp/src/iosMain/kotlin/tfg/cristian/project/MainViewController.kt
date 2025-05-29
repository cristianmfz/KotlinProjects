package tfg.cristian.project

import androidx.compose.ui.window.ComposeUIViewController
import tfg.cristian.project.di.initializeKoin

fun MainViewController() = ComposeUIViewController(configure = { initializeKoin() }) {
    App(IOSUserRepository())
}