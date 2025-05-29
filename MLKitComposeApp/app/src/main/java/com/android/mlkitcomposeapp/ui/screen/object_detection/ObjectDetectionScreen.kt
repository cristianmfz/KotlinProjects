package com.android.mlkitcomposeapp.ui.screen.object_detection

import android.Manifest
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.android.mlkitcomposeapp.R
import com.android.mlkitcomposeapp.analyzer.ObjectDetectionAnalyzer
import com.android.mlkitcomposeapp.ui.common.components.CameraView
import com.android.mlkitcomposeapp.ui.common.utils.*
import com.google.mlkit.vision.objects.DetectedObject

@Composable
fun ObjectDetectionScreen(navController: NavController) {
    val cameraPermissionState = rememberPermissionRequest(permission = Manifest.permission.CAMERA)

    PermissionRequired(
        permissionState = cameraPermissionState,
        permissionDeniedMessage = stringResource(id = R.string.camera_permission_message),
        permissionNotGrantedContent = {
            LaunchedEffect(Unit) {
                cameraPermissionState.launcher.launch(Manifest.permission.CAMERA)
            }
        },
        content = {
            ScanSurface(navController = navController)
        }
    )
}

@Composable
fun ScanSurface(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val detectedObjects = remember { mutableStateListOf<DetectedObject>() }

    val screenWidth = remember { mutableIntStateOf(context.resources.displayMetrics.widthPixels) }
    val screenHeight = remember { mutableIntStateOf(context.resources.displayMetrics.heightPixels) }

    val imageWidth = remember { mutableIntStateOf(480) }
    val imageHeight = remember { mutableIntStateOf(640) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            analyzer = ObjectDetectionAnalyzer { objects, width, height ->
                if (detectedObjects.size != objects.size || !detectedObjects.containsAll(objects)) {
                    detectedObjects.clear()
                    detectedObjects.addAll(objects)
                }
                imageWidth.intValue = width
                imageHeight.intValue = height
            }
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(id = R.string.object_detection_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
            DrawDetectedObjects(detectedObjects, imageWidth.intValue, imageHeight.intValue, screenWidth.intValue, screenHeight.intValue)
        }
    }
}

@Composable
fun DrawDetectedObjects(objects: List<DetectedObject>, imageWidth: Int, imageHeight: Int, screenWidth: Int, screenHeight: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        objects.forEach {
            val boundingBox = it.boundingBox.toComposeRect()
            val topLeft = adjustPoint(PointF(boundingBox.topLeft.x, boundingBox.topLeft.y), imageWidth, imageHeight, screenWidth, screenHeight)
            val size = adjustSize(boundingBox.size, imageWidth, imageHeight, screenWidth, screenHeight)

            drawBounds(topLeft, size, Color.Yellow, 10f)
        }
    }
}