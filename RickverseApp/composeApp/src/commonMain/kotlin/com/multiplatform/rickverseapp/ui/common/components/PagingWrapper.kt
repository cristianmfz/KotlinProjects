package com.multiplatform.rickverseapp.ui.common.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import com.multiplatform.rickverseapp.ui.common.components.PagingType.*

enum class PagingType {
    ROW,
    COLUMN,
    VERTICAL_GRID
}

@Composable
fun <T : Any> PagingWrapper(
    pagingType: PagingType,
    pagingItems: LazyPagingItems<T>,
    itemView: @Composable (T) -> Unit,
    initialView: @Composable () -> Unit = {},
    emptyView: @Composable () -> Unit = {},
    extraItemsView: @Composable () -> Unit = {},
    header: @Composable () -> Unit = {},
) {

    when {
        pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0 -> {
            //Carga inicial
            initialView()
        }

        pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 -> {
            emptyView()
        }

        else -> {

            when (pagingType) {
                ROW -> {
                    LazyRowTarget(pagingItems, itemView)
                }

                COLUMN -> {
                    LazyColumn {
                        item { header() }
                        items(pagingItems.itemCount) { pos ->
                            pagingItems[pos]?.let { item ->
                                itemView(item)
                            }
                        }
                    }
                }

                VERTICAL_GRID -> {
                    LazyVerticalGridTarget(pagingItems, itemView, header)
                }
            }


            if (pagingItems.loadState.append is LoadState.Loading) {
                extraItemsView()
            }

        }
    }

}