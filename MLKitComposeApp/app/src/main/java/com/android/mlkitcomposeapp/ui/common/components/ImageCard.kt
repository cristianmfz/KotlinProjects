package com.android.mlkitcomposeapp.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun ImageCard(title: String, description: String, imageUrl: String, onCardClick: () -> Unit) {
    val onClick = rememberDelayedClick(onCardClick, 1000L)

    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.clickable(onClick = onClick)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Composable
fun rememberDelayedClick(onClick: () -> Unit, delayTime: Long = 1000L): () -> Unit {
    var isClickable by remember { mutableStateOf(true) }

    LaunchedEffect(isClickable) {
        if (!isClickable) {
            delay(delayTime)
            isClickable = true
        }
    }

    return {
        if (isClickable) {
            isClickable = false
            onClick()
        }
    }
}