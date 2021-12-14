package com.compose.template.remote

import com.compose.template.model.ApiResult
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPokemonList(): Response<ApiResult>

}