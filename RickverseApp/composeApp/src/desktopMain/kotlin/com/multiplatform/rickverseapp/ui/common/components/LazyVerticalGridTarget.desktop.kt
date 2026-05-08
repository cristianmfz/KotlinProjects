package com.multiplatform.rickverseapp.ui.common.components

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.multiplatform.rickverseapp.ui.common.theme.BackgroundPrimaryColor
import com.multiplatform.rickverseapp.ui.common.theme.Green

@Composable
actual fun <T : Any> LazyVerticalGridTarget(
    pagingItems: LazyPagingItems<T>,
    itemView: @Composable (T) -> Unit,
    header: @Composable () -> Unit
) {
    Box (contentAlignment = Alignment.TopEnd){
        val state = rememberLazyGridState()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(BackgroundPrimaryColor).padding(horizontal = 16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) { header() }
            items(pagingItems.itemCount) { pos ->
                pagingItems[pos]?.let { item ->
                    itemView(item)
                }
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(state), style = LocalScrollbarStyle.current.copy(
                unhoverColor = Green.copy(alpha = 0.4f),
                hoverColor = Green,
                thickness = 10.dp
            )
        )
    }
}