package com.example.githubusers.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponseItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,
) : Parcelable
