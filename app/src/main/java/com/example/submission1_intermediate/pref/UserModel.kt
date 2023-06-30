package com.example.submission1_intermediate.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var token: String,
    var isLogin: Boolean
): Parcelable
