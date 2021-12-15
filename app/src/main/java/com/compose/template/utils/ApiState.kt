package com.compose.template.utils

sealed class ApiState<out T> {

    data class Success<out T>(val data: T): ApiState<T>()

    object Loading: ApiState<Nothing>()

    data class Failure(
        val code: Int? = null,
        val errorMessage: String? = null
    ): ApiState<Nothing>()

    object NetworkError: ApiState<Nothing>()
}