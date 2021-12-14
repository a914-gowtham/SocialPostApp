package com.compose.template.data

import com.compose.template.domain.ApiResult
import retrofit2.Response

interface PostDataSource {

    suspend fun getAllPost(): Response<ApiResult>

}