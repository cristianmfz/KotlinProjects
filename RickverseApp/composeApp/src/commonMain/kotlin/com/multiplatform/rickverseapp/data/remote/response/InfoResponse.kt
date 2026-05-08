package com.multiplatform.rickverseapp.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class InfoResponse(
    val pages:Int,
    val next:String?,
    val prev:String?
)