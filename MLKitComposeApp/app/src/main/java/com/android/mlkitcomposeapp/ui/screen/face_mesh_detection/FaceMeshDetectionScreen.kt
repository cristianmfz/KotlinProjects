package com.android.mlkitcomposeapp.ui.screen.face_mesh_detection

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
import com.android.mlkitcomposeapp.analyzer.FaceMeshDetectionAnalyzer
import com.android.mlkitcomposeapp.ui.common.components.CameraView
import com.android.mlkitcomposeapp.ui.common.utils.*
import com.google.mlkit.vision.facemesh.FaceMesh

@Composable
fun FaceMeshDetectionScreen(navController: NavController) {
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
    val faces = remember { mutableStateListOf<FaceMesh>() }

    val screenWidth = remember { mutableIntStateOf(context.resources.displayMetrics.widthPixels) }
    val screenHeight = remember { mutableIntStateOf(context.resources.displayMetrics.heightPixels) }

    val imageWidth = remember { mutableIntStateOf(0) }
    val imageHeight = remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            analyzer = FaceMeshDetectionAnalyzer { meshes, width, height ->
                if (faces.size != meshes.size || !faces.containsAll(meshes)) {
                    faces.clear()
                    faces.addAll(meshes)
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
                    text = stringResource(id = R.string.face_mesh_detection_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
        DrawFaces(faces = faces, imageWidth.intValue, imageHeight.intValue, screenWidth.intValue, screenHeight.intValue)
    }
}

@Composable
fun DrawFaces(faces: List<FaceMesh>, imageWidth: Int, imageHeight: Int, screenWidth: Int, screenHeight: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        faces.forEach { face ->
            val boundingBox = face.boundingBox.toComposeRect()
            val topLeft = adjustPoint(PointF(boundingBox.topLeft.x, boundingBox.topLeft.y), imageWidth, imageHeight, screenWidth, screenHeight)
            val size = adjustSize(boundingBox.size, imageWidth, imageHeight, screenWidth, screenHeight)
            drawBounds(topLeft, size, Color.Yellow, 5f)

            face.allPoints.forEach {
                val landmark = adjustPoint(PointF(it.position.x, it.position.y), imageWidth, imageHeight, screenWidth, screenHeight)
                drawLandmark(landmark, Color.Cyan, 3f)
            }

            face.allTriangles.forEach { triangle ->
                val points = triangle.allPoints.map {
                    adjustPoint(PointF(it.position.x, it.position.y), imageWidth, imageHeight, screenWidth, screenHeight)
                }
                drawTriangle(points, Color.Cyan, 1f)
            }
        }
    }
}