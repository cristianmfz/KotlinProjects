package com.android.mlkitcomposeapp.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Home : Destination()

    @Serializable
    data object BarcodeScanning : Destination()

    @Serializable
    data object FaceDetection : Destination()

    @Serializable
    data object FaceMeshDetection : Destination()

    @Serializable
    data object TextRecognition : Destination()

    @Serializable
    data object ImageLabeling : Destination()

    @Serializable
    data object ObjectDetection : Destination()
}