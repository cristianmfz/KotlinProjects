package com.android.paging3compose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.paging3compose.ui.model.CharacterModel
import okio.IOException
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(private val api: DragonApiService): PagingSource<Int, CharacterModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        return try {
            val page = params.key ?: 0
            val response = api.getCharacters(page)
            val characters = response.results

            // Verificar si la lista de personajes está vacía para indicar que no hay más páginas
            if (characters.isEmpty()) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (page > 0) page - 1 else null,
                    nextKey = null // No hay más páginas
                )
            }

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (response.info.next != null && characters.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = characters.map { it.toUi() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}