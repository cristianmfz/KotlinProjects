package com.android.mlkitcomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.mlkitcomposeapp.ui.screen.barcode_scanning.BarcodeScanningScreen
import com.android.mlkitcomposeapp.ui.screen.face_detection.FaceDetectionScreen
import com.android.mlkitcomposeapp.ui.screen.face_mesh_detection.FaceMeshDetectionScreen
import com.android.mlkitcomposeapp.ui.screen.home.HomeScreen
import com.android.mlkitcomposeapp.ui.screen.image_labeling.ImageLabelingScreen
import com.android.mlkitcomposeapp.ui.screen.object_detection.ObjectDetectionScreen
import com.android.mlkitcomposeapp.ui.screen.text_recognition.TextRecognitionScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.Home) {
        composable<Destination.Home> {
            HomeScreen(navController)
        }
        composable<Destination.BarcodeScanning> {
            BarcodeScanningScreen(navController)
        }
        composable<Destination.FaceDetection> {
            FaceDetectionScreen(navController)
        }
        composable<Destination.FaceMeshDetection> {
            FaceMeshDetectionScreen(navController)
        }
        composable<Destination.TextRecognition> {
            TextRecognitionScreen(navController)
        }
        composable<Destination.ImageLabeling> {
            ImageLabelingScreen(navController)
        }
        composable<Destination.ObjectDetection> {
            ObjectDetectionScreen(navController)
        }
    }
}