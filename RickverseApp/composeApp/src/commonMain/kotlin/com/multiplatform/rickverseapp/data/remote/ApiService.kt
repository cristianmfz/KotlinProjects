package com.multiplatform.rickverseapp.data.remote

import com.multiplatform.rickverseapp.data.remote.response.CharacterResponse
import com.multiplatform.rickverseapp.data.remote.response.CharactersWrapperResponse
import com.multiplatform.rickverseapp.data.remote.response.EpisodeResponse
import com.multiplatform.rickverseapp.data.remote.response.EpisodesWrapperResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val client: HttpClient) {

    suspend fun getSingleCharacter(id:String): CharacterResponse {
        return client.get("/api/character/$id").body()
    }

    suspend fun getAllCharacters(page:Int): CharactersWrapperResponse {
        return client.get("/api/character"){
            parameter("page", page)
        }.body()
    }

    suspend fun getAllEpisodes(page: Int): EpisodesWrapperResponse {
        return client.get("/api/episode"){
            parameter("page", page)
        }.body()
    }

    suspend fun getEpisodes(episodes: String):List<EpisodeResponse> {
        return client.get("/api/episode/$episodes").body()
    }

    suspend fun getSingleEpisode(episodeId: String):EpisodeResponse {
        return client.get("/api/episode/$episodeId").body()
    }
}