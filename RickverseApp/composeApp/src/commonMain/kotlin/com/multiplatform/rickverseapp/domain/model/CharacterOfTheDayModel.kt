package com.multiplatform.rickverseapp.domain.model

import com.multiplatform.rickverseapp.data.database.entity.CharacterOfTheDayEntity
import kotlinx.serialization.json.Json

data class CharacterOfTheDayModel(
    val characterModel: CharacterModel,
    val selectedDay:String
) {
    fun toEntity(): CharacterOfTheDayEntity {
        return CharacterOfTheDayEntity(
            id = characterModel.id,
            isAlive =  characterModel.isAlive,
            image = characterModel.image,
            name = characterModel.name,
            selectedDay = selectedDay,
            species = characterModel.species,
            gender = characterModel.gender,
            origin = characterModel.origin,
            episodes = Json.encodeToString(characterModel.episodes)
        )
    }
}