package com.compose.template.usecases

import com.compose.template.model.Post
import com.compose.template.remote.PostDataSourceImpl
import com.compose.template.utils.ApiState
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postDataSource: PostDataSourceImpl
) {

    suspend fun execute(): ApiState<List<Post>>{
        return try {
            val response= postDataSource.getAllPost()
            if(response.isSuccessful)
                ApiState.Success(response.body()?.data ?: emptyList())
            else
                ApiState.Failure(response.code(),response.message())
        }catch (e: Exception){
            ApiState.Failure(100,"No internet connection!")
        }

    }
}