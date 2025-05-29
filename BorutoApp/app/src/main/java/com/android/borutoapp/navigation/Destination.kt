package com.android.borutoapp.navigation

import kotlinx.serialization.Serializable

const val HERO_ID = "heroId"

sealed class Destination {
    @Serializable
    data object Splash : Destination()

    @Serializable
    data object Welcome : Destination()

    @Serializable
    data object Home : Destination()

    @Serializable
    data class Detail(val heroId: Int) : Destination()

    @Serializable
    data object Search : Destination()
}