package com.example.submission1_intermediate.recent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission1_intermediate.api.Retrofit
import com.example.submission1_intermediate.paging.StoryRepo
import com.example.submission1_intermediate.pref.UserModel
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.response.StoryResponse
import com.example.submission1_intermediate.response.story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceentViewModel(private val pref: UserPreference): ViewModel() {
    val data = MutableLiveData<ArrayList<story>>()
    fun getStoryPersonal(token : String,id:String){
        val retro = Retrofit.getApiService().getStoryPersonal("Bearer $token",id)
        retro.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if(response.isSuccessful) {
                    data.postValue(response.body()?.listStory as ArrayList<story>)
                    Log.d("Result MapsModel :", (response.body()?.listStory as List<story>).toString())
                }else{
                    Log.d("Error :", response.message().toString())
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {

                Log.d("Failure", t.message!!)
            }
        })

    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    fun getListStory(): MutableLiveData<ArrayList<story>> {
        return data
    }
}