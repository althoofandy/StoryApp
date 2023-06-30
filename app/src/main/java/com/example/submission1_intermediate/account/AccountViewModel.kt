package com.example.submission1_intermediate.account

import android.content.Context
import androidx.lifecycle.*
import com.example.submission1_intermediate.paging.Injection
import com.example.submission1_intermediate.paging.StoryRepo
import com.example.submission1_intermediate.pref.UserModel
import com.example.submission1_intermediate.pref.UserPreference
import kotlinx.coroutines.launch

class AccountViewModel(private val repo: StoryRepo, private val pref: UserPreference): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}
class AccountViewModelFactory(private val context: Context, private val pref: UserPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(Injection.provideRepository(context),pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}