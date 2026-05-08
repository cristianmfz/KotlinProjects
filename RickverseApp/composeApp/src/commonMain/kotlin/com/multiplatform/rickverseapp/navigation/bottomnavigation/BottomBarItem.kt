package com.multiplatform.rickverseapp.navigation.bottomnavigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.rickverseapp.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import rickverseapp.composeapp.generated.resources.Res
import rickverseapp.composeapp.generated.resources.ic_characters
import rickverseapp.composeapp.generated.resources.ic_player

sealed class BottomBarItem {
    abstract val destination: Destination
    abstract val title: String
    abstract val icon: @Composable () -> Unit

    data class Episodes(
        override val destination: Destination = Destination.Episodes,
        override val title: String = "Episodes",
        override val icon: @Composable () -> Unit = {
            Icon(
                painter = painterResource(Res.drawable.ic_player),
                "",
                modifier = Modifier.size(24.dp)
            )
        }
    ) : BottomBarItem()

    data class Characters(
        override val destination: Destination = Destination.Characters,
        override val title: String = "Characters",
        override val icon: @Composable () -> Unit = {
            Icon(
                painter = painterResource(Res.drawable.ic_characters),
                "",
                modifier = Modifier.size(24.dp)
            )
        }
    ) : BottomBarItem()
}