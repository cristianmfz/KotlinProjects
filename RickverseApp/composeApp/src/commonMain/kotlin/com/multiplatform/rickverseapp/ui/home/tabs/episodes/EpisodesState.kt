package com.multiplatform.rickverseapp.ui.home.tabs.episodes

import app.cash.paging.PagingData
import com.multiplatform.rickverseapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class EpisodesState(
    val characters: Flow<PagingData<EpisodeModel>> = emptyFlow(),
    val playVideo:String = ""
)