package com.android.paging3compose.data.response

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("first") val first: String,
    @SerializedName("previous") val prev: String?,
    @SerializedName("next") val next: String?,
    @SerializedName("last") val last: String
)