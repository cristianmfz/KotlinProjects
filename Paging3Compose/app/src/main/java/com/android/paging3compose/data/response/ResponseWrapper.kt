package com.android.paging3compose.data.response

import com.google.gson.annotations.SerializedName

data class ResponseWrapper(
    @SerializedName("links") val info: InfoResponse,
    @SerializedName("items") val results: List<CharacterResponse>
)