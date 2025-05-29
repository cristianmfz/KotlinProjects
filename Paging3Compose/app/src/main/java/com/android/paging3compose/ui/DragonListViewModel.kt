package com.android.paging3compose.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.android.paging3compose.data.DragonRepository
import com.android.paging3compose.ui.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(dragonRepository: DragonRepository) : ViewModel() {
    val characters: Flow<PagingData<CharacterModel>> = dragonRepository.getAllCharacters()
}