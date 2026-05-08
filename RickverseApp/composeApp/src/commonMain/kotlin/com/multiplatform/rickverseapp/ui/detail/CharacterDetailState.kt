package com.multiplatform.rickverseapp.ui.detail

import com.multiplatform.rickverseapp.domain.model.CharacterModel
import com.multiplatform.rickverseapp.domain.model.EpisodeModel

data class CharacterDetailState(
    val characterModel: CharacterModel,
    val episodes:List<EpisodeModel>? = null
)