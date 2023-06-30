package com.example.submission1_intermediate.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1_intermediate.api.Retrofit
import com.example.submission1_intermediate.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    val data = MutableLiveData<RegisterResponse>()
    fun register(name : String?,email: String?,password: String?){
        val retro = Retrofit.getApiService().register(name,email, password)
            retro.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if(response.isSuccessful) {
                    data.postValue(response.body())
                    }else{
                        Log.d("Error :", response.message())
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }
    fun getRegister(): LiveData<RegisterResponse>{
        return data
    }
}