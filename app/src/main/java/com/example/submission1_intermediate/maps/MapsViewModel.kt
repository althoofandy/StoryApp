package com.example.submission1_intermediate.maps

import android.util.Log
import androidx.lifecycle.*
import com.example.submission1_intermediate.api.Retrofit
import com.example.submission1_intermediate.pref.UserModel
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.response.MapsResponse
import com.example.submission1_intermediate.response.MapsStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val pref: UserPreference): ViewModel() {

    val data = MutableLiveData<ArrayList<MapsStory>>()
    fun getMapsStory(token:String){
        val retro = Retrofit.getApiService().getStoryMaps("Bearer $token")
        retro.enqueue(object : Callback<MapsResponse> {
            override fun onResponse(call: Call<MapsResponse>, response: Response<MapsResponse>) {
                if(response.isSuccessful) {
                    data.postValue(response.body()?.listStory as ArrayList<MapsStory>)
                    Log.d("Result MapsModel :", (response.body()?.listStory as List<MapsStory>).toString())
                }else{
                    Log.d("Error :", response.message().toString())
                }
            }
            override fun onFailure(call: Call<MapsResponse>, t: Throwable) {

                Log.d("Failure", t.message!!)
            }
        })
    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    fun getMaps(): LiveData<ArrayList<MapsStory>> {
        return data
    }

}