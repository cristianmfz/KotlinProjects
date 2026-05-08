package com.multiplatform.rickverseapp.domain

import app.cash.paging.PagingData
import com.multiplatform.rickverseapp.domain.model.CharacterModel
import com.multiplatform.rickverseapp.domain.model.CharacterOfTheDayModel
import com.multiplatform.rickverseapp.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getSingleCharacter(id:String): CharacterModel
    fun getAllCharacters(): Flow<PagingData<CharacterModel>>
    fun getAllEpisodes(): Flow<PagingData<EpisodeModel>>
    suspend fun getCharacterDB(): CharacterOfTheDayModel?
    suspend fun saveCharacterDB(characterOfTheDayModel:CharacterOfTheDayModel)
    suspend fun getEpisodesForCharacter(episodes: List<String>):List<EpisodeModel>
}