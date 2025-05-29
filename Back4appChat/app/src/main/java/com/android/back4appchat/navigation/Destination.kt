package com.android.back4appchat.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Auth: Destination()

    @Serializable
    data object Home: Destination()

    @Serializable
    data object CreateRoom: Destination()

    @Serializable
    data class Chat(val id: String): Destination()
}