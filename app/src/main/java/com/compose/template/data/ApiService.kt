package com.compose.template.data

import com.compose.template.domain.ApiResult
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPokemonList(): Response<ApiResult>

}