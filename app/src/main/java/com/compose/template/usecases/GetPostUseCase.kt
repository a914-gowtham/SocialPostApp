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
            LogMessage.v("Status Code ${response.code()}")

            when {
                // no cache available locally/no internet for the first time
                response.code()==501 -> ApiState.Failure(501,"No internet connection!")
                response.isSuccessful -> ApiState.Success(response.body()?.data ?: emptyList())
                else -> ApiState.Failure(response.code(),response.message())
            }
        }catch (e: Exception){
            ApiState.Failure(100,"No internet connection!")
        }

    }
}