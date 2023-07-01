package com.example.submission1_intermediate.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1_intermediate.api.Retrofit
import com.example.submission1_intermediate.pref.UserModel
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference):ViewModel() {
    val data = MutableLiveData<LoginResponse>()
    val msg = MutableLiveData<String>()
    fun login(email: String, password: String): LiveData<String>{
        val retro = Retrofit.getApiService().login(email,password)
            retro.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(response.isSuccessful) {
                        data.postValue(response.body())
                        saveUser(UserModel(response.body()?.loginResult?.userId!!,response.body()?.loginResult?.token!!,response.body()?.loginResult?.name!!,true))
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                        val errorMessage = errorResponse?.message.toString()
                        Log.d("Error:", errorMessage)
                        msg.postValue(errorMessage)
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Log.d("Failure", t.message!!)
                }
            })
        return msg
    }
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
    fun getLogin(): LiveData<LoginResponse> {
            return data
    }
}