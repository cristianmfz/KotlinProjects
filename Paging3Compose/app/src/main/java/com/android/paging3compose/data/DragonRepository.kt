package com.android.paging3compose.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.paging3compose.ui.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DragonRepository @Inject constructor(private val api: DragonApiService) {
    companion object {
        const val MAX_ITEMS = 10
        const val PREFETCH_ITEMS = 3
    }

    fun getAllCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {CharacterPagingSource(api)}).flow
    }
}