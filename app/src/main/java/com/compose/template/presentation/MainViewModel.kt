package com.compose.template.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.template.usecases.GetPostUseCase
import com.compose.template.domain.Post
import com.compose.template.utils.ApiState
import com.compose.template.utils.LogMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
) : ViewModel() {

    private val _postState = MutableStateFlow<ApiState<List<Post>>>(ApiState.Loading)
    val postState: StateFlow<ApiState<List<Post>>>
        get() = _postState

    init {
        LogMessage.v("MainViewModel init")
        fetchAllPost()
    }


    fun fetchAllPost() = viewModelScope.launch {
        LogMessage.v("getPosts")
        _postState.value = ApiState.Loading
        _postState.value = getPostUseCase.execute()
    }
}