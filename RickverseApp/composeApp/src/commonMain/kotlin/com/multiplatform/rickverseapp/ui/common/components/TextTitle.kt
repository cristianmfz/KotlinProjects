package com.multiplatform.rickverseapp.ui.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.multiplatform.rickverseapp.ui.common.theme.DefaultTextColor

@Composable
fun TextTitle(text:String){
    Text(text.uppercase(), color = DefaultTextColor, fontWeight = FontWeight.Bold)
}