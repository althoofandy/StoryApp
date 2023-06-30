package com.example.submission1_intermediate.response

import com.google.gson.annotations.SerializedName

data class StoryResponse (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<story>
)
data class story(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("photoUrl")
    val photoUrl: String?,

    @field:SerializedName("createdAt")
    val createdAt: String?,

    @field:SerializedName("lat")
    val lat: String?,

    @field:SerializedName("lon")
    val lon: String?

)