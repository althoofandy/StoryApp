package com.example.submission1_intermediate.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submission1_intermediate.api.Api
import com.example.submission1_intermediate.response.story

class StoryRepo( private val apiService: Api) {
    fun getStory(token: String): LiveData<PagingData<story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPaging(apiService,token)
            }
        ).liveData
    }

}