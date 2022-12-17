package com.example.githubusers.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponseItem(
    @field:SerializedName("repos_url")
    val reposUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("id")
    val id: Int,
) : Parcelable
