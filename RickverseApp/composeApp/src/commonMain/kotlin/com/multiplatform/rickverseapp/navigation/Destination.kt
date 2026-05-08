package com.multiplatform.rickverseapp.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Home: Destination()

    @Serializable
    data class CharacterDetail(val characterModel: String): Destination()

    //BottomNav
    @Serializable data object Episodes: Destination()
    @Serializable data object Characters: Destination()
}