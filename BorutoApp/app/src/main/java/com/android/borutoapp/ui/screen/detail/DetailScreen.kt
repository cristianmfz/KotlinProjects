package com.android.borutoapp.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.borutoapp.util.Constants.BASE_URL
import com.android.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.android.borutoapp.util.PaletteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    navController: NavHostController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val selectedHero by detailViewModel.selectedHero.collectAsState()
    val colorPalette by detailViewModel.colorPalette
    val context = LocalContext.current

    LaunchedEffect(key1 = selectedHero) {
        if (selectedHero != null && colorPalette.isEmpty()) {
            detailViewModel.generateColorPalette()
        }
    }

    if (selectedHero != null) {
        DetailContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalette.takeIf { it.isNotEmpty() } ?: emptyMap()
        )
    }

    LaunchedEffect(key1 = true) {
        detailViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = selectedHero?.image?.let {
                        convertImageUrlToBitmap(
                            imageUrl = "$BASE_URL$it",
                            context = context
                        )
                    }
                    if (bitmap != null) {
                        detailViewModel.setColorPalette(
                            colors = extractColorsFromBitmap(bitmap = bitmap)
                        )
                    }
                }
            }
        }
    }
}