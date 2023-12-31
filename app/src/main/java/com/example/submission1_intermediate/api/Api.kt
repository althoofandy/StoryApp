package com.example.submission1_intermediate.api
import com.example.submission1_intermediate.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String?,
        @Field("email") email : String?,
        @Field("password") password : String?
    ): Call<RegisterResponse>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") auth : String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") auth : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<FileUploadResponse>

    @GET("stories")
    fun getStoryMaps(
        @Header("Authorization") auth : String,
        @Query("location") location: Int=1
    ): Call<MapsResponse>

    @GET("stories/{id}")
     fun getStoryPersonal(
        @Header("Authorization") auth : String,
        @Path("id") id: String,
    ): Call<StoryResponse>
}