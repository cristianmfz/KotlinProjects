package com.multiplatform.windows95.ui.components.rightmenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuDivider(){
    Column(Modifier.padding(horizontal = 2.dp)) {
        HorizontalDivider(thickness = 2.dp, color = Color.Black.copy(0.5f))
        HorizontalDivider(thickness = 2.dp, color = Color.White)
    }
}