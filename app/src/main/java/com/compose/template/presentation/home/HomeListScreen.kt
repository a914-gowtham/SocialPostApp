package com.compose.template.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.template.presentation.MainViewModel
import com.compose.template.utils.ApiState

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
) {
    val lazyPostList = viewModel.postState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Posts") }) },
        modifier = Modifier.fillMaxSize()
    ) {

        if (lazyPostList.value is ApiState.Success) {
            val posts = lazyPostList.value as ApiState.Success
            LazyColumn(contentPadding = it,modifier = Modifier.padding(horizontal = 16.dp)) {
                items(posts.data) { post ->
                    PostRow(post = post)
                }
            }
        }

        if (lazyPostList.value is ApiState.Loading)
            CircularProgressBar()

        if (lazyPostList.value is ApiState.Failure) {
            val failureState = lazyPostList.value as ApiState.Failure
            ErrorView(failureState.errorMessage ?: "Something went wrong!") {
                viewModel.fetchAllPost()
            }
        }
    }
}