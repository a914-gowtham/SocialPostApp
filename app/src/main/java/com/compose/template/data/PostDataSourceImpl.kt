package com.compose.template.remote

import com.compose.template.model.ApiResult
import retrofit2.Response
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): PostDataSource {

    override suspend fun getAllPost(): Response<ApiResult> {
        return apiService.getPokemonList()
    }
}