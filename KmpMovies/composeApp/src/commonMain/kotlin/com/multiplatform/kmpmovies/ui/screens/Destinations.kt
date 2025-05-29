package com.multiplatform.kmpmovies.ui.screens

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detail(val movieId: Int)