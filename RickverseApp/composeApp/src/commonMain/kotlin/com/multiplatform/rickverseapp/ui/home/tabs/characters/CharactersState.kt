package com.multiplatform.rickverseapp.ui.home.tabs.characters

import app.cash.paging.PagingData
import com.multiplatform.rickverseapp.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CharactersState(
    val characterOfTheDay:CharacterModel? = null,
    val characters: Flow<PagingData<CharacterModel>> = emptyFlow()
)