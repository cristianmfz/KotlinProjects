package com.android.mlkitcomposeapp.ui.screen.image_labeling

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.android.mlkitcomposeapp.R
import com.android.mlkitcomposeapp.analyzer.ImageLabelingAnalyzer
import com.android.mlkitcomposeapp.ui.common.components.CameraView
import com.android.mlkitcomposeapp.ui.common.utils.PermissionRequired
import com.android.mlkitcomposeapp.ui.common.utils.rememberPermissionRequest
import com.google.mlkit.vision.label.ImageLabel

@Composable
fun ImageLabelingScreen(navController: NavController) {
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
    val imageLabels = remember { mutableStateListOf<ImageLabel>() }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            analyzer = ImageLabelingAnalyzer { newLabels ->
                val updatedLabels = newLabels.filterNot { newLabel ->
                    imageLabels.any { it.text == newLabel.text }
                }

                if (updatedLabels.isNotEmpty()) {
                    imageLabels.clear()
                    imageLabels.addAll(newLabels)
                }
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
                    text = stringResource(id = R.string.image_labeling_detection_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) {
                BasicTextField(
                    value = imageLabels.joinToString(separator = "\n") { it.text },
                    onValueChange = {},
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions.Default,
                    readOnly = true
                )
            }
        }
    }
}