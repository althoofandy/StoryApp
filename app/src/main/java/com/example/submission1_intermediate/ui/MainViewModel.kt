package com.example.submission1_intermediate.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission1_intermediate.paging.Injection
import com.example.submission1_intermediate.paging.StoryRepo
import com.example.submission1_intermediate.pref.UserModel
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.response.story
import kotlinx.coroutines.launch

class MainViewModel(private val repo: StoryRepo , private val pref: UserPreference) : ViewModel(){
    val listUser = MutableLiveData<ArrayList<story>>()

    fun getStoryPage(token:String):LiveData<PagingData<story>>{
        return repo.getStory(token).cachedIn(viewModelScope)
    }

    fun getListStory(): MutableLiveData<ArrayList<story>> {
        return listUser
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

}
class ViewModelFac(private val context: Context,private val pref: UserPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context),pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
