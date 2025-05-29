package com.android.mlkitcomposeapp.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.mlkitcomposeapp.navigation.Destination
import com.android.mlkitcomposeapp.ui.common.components.ImageCard
import com.android.mlkitcomposeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                    }
                },
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        contentColor = MaterialTheme.colorScheme.primary,
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = paddingValues
            ) {
                item {
                    ImageCard(
                        title = stringResource(id = R.string.barcode_scanning_title),
                        description = stringResource(id = R.string.barcode_scanning_description),
                        imageUrl = stringResource(id = R.string.barcode_scanning_url),
                        onCardClick = { navController.navigate(Destination.BarcodeScanning) }
                    )
                }

                item {
                    ImageCard(
                        title = stringResource(id = R.string.face_detection_title),
                        description = stringResource(id = R.string.face_detection_description),
                        imageUrl = stringResource(id = R.string.face_detection_url),
                        onCardClick = { navController.navigate(Destination.FaceDetection) }
                    )
                }

                item {
                    ImageCard(
                        title = stringResource(id = R.string.face_mesh_detection_title),
                        description = stringResource(id = R.string.face_mesh_detection_description),
                        imageUrl = stringResource(id = R.string.face_mesh_detection_url),
                        onCardClick = { navController.navigate(Destination.FaceMeshDetection) }
                    )
                }

                item {
                    ImageCard(
                        title = stringResource(id = R.string.text_recognition_title),
                        description = stringResource(id = R.string.text_recognition_description),
                        imageUrl = stringResource(id = R.string.text_recognition_url),
                        onCardClick = { navController.navigate(Destination.TextRecognition) }
                    )
                }

                item {
                    ImageCard(
                        title = stringResource(id = R.string.image_labeling_detection_title),
                        description = stringResource(id = R.string.image_labeling_detection_description),
                        imageUrl = stringResource(id = R.string.image_labeling_detection_url),
                        onCardClick = { navController.navigate(Destination.ImageLabeling) }
                    )
                }

                item {
                    ImageCard(
                        title = stringResource(id = R.string.object_detection_title),
                        description = stringResource(id = R.string.object_detection_description),
                        imageUrl = stringResource(id = R.string.object_detection_url),
                        onCardClick = { navController.navigate(Destination.ObjectDetection) }
                    )
                }
            }
        }
    )
}