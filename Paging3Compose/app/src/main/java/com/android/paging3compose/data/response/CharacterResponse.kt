package com.android.paging3compose.data.response

import com.android.paging3compose.ui.model.CharacterModel
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("race") val race: String,
    @SerializedName("image") val image: String
) {
    fun toUi(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            race = race, //isSaiyan = (race=="Saiyan")
            image = image
        )
    }
}