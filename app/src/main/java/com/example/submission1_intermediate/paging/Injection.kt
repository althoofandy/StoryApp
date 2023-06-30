package com.example.submission1_intermediate.paging

import android.content.Context
import com.example.submission1_intermediate.api.Retrofit
import com.example.submission1_intermediate.pref.UserPreference

object Injection {
    fun provideRepository(context: Context): StoryRepo {
        val apiService = Retrofit.getApiService()
        return StoryRepo(apiService)
    }
}