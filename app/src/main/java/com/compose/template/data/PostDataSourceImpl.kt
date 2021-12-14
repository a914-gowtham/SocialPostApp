package com.compose.template.data

import com.compose.template.domain.ApiResult
import retrofit2.Response
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): PostDataSource {

    override suspend fun getAllPost(): Response<ApiResult> {
        return apiService.getPokemonList()
    }
}