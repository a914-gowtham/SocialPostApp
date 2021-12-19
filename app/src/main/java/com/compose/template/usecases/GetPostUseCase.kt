package com.compose.template.usecases

import com.compose.template.domain.Post
import com.compose.template.data.PostDataSourceImpl
import com.compose.template.utils.ApiState
import com.compose.template.utils.LogMessage
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postDataSource: PostDataSourceImpl
) {

    suspend fun execute(): ApiState<List<Post>>{
        return try {
            val response= postDataSource.getAllPost()

            when {
                // no cache available locally/no internet for the first time
                response.code()==504 -> ApiState.Failure(504,"No local cache! Please connect internet")
                response.isSuccessful -> ApiState.Success(response.body()?.data ?: emptyList())
                else -> ApiState.Failure(response.code(),response.message())
            }
        }catch (e: Exception){
            ApiState.Failure(100,"No internet connection!")
        }

    }
}