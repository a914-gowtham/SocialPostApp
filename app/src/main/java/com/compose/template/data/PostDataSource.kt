package com.compose.template.remote

import com.compose.template.model.ApiResult
import retrofit2.Response

interface PostDataSource {

    suspend fun getAllPost(): Response<ApiResult>

}