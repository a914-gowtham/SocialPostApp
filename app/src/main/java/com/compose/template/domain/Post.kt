package com.compose.template.domain

import com.squareup.moshi.Json

data class Post(
    val body: String,
    val id: Int,
    val title: String,
    @field:Json(name = "user_id")
    val userId: Int
)