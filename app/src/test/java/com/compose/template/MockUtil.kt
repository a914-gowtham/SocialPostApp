package com.compose.template

import com.compose.template.domain.ApiResult
import com.compose.template.domain.Post

object MockUtil {

    fun mockPost() = Post(
        id = 0, title = "A test title",
        body = "a test body",
        userId = 264
    )

    fun mockPostList() = ApiResult(data = listOf(mockPost()))

}