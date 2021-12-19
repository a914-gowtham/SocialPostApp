package com.compose.template.usecases

import com.compose.template.MainCoroutinesRule
import com.compose.template.MockUtil
import com.compose.template.data.ApiService
import com.compose.template.data.PostDataSourceImpl
import com.compose.template.utils.ApiState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response
import com.google.common.truth.Truth.assertThat

class GetPostUseCaseTest {

    private lateinit var getPostUseCase: GetPostUseCase

    private val apiService: ApiService = mock()
    private val dataSourceImpl = spy(PostDataSourceImpl(apiService))

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()


    @Before
    fun setup() {
        getPostUseCase = GetPostUseCase(dataSourceImpl)
    }

    @Test
    fun fetchPostListFromNetworkTest() = runBlocking {
        val mockData = MockUtil.mockPostList()
        whenever(apiService.getPokemonList()).thenReturn(
            Response.success(200,mockData))

        val result= getPostUseCase.execute().apply {
            assertThat(this).isInstanceOf(ApiState.Success::class.java)
        } as ApiState.Success

        assertThat(result.data).isSameInstanceAs(mockData.data)

        verify(dataSourceImpl,times(1)).getAllPost()
        verify(apiService, times(1)).getPokemonList()
        Unit //JUnit test should return Unit
    }

}