package com.android.mlkitcomposeapp.ui.common.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

data class PermissionState(
    val launcher: ManagedActivityResultLauncher<String, Boolean>,
    val hasPermission: MutableState<Boolean>,
    val permissionDenied: MutableState<Boolean>
)

@Composable
fun rememberPermissionRequest(permission: String): PermissionState {
    val context = LocalContext.current
    val hasPermission = remember { mutableStateOf(
        context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    ) }

    val permissionDenied = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermission.value = isGranted
            permissionDenied.value = !isGranted
        }
    )

    return remember { PermissionState(launcher, hasPermission, permissionDenied) }
}

@Composable
fun PermissionRequired(
    permissionState: PermissionState,
    permissionDeniedMessage: String,
    permissionNotGrantedContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        if (permissionState.hasPermission.value) {
            content()
        } else {
            permissionNotGrantedContent()

            LaunchedEffect(permissionState.permissionDenied.value) {
                if (permissionState.permissionDenied.value) {
                    snackBarHostState.showSnackbar(
                        message = permissionDeniedMessage,
                        actionLabel = "Open Settings",
                        duration = SnackbarDuration.Long
                    ).also { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            openAppSettings(context)
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = "package:${context.packageName}".toUri()
    }
    context.startActivity(intent)
}