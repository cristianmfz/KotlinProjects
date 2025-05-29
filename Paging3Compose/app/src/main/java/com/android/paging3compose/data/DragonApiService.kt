package com.android.paging3compose.data

import com.android.paging3compose.data.response.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface DragonApiService {
    @GET("/api/characters/")
    suspend fun getCharacters(@Query("page") page: Int): ResponseWrapper
}