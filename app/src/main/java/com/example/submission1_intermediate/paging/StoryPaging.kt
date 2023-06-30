package com.example.submission1_intermediate.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submission1_intermediate.api.Api
import com.example.submission1_intermediate.response.story

class StoryPaging(private val apiService: Api,private val token:String) : PagingSource<Int, story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, story> {
        return try {

            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStory("Bearer $token",page, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}