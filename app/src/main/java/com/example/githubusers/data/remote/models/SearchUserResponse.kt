package com.example.githubusers.data.remote.models

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @field:SerializedName("items")
    val items: List<UserResponseItem>
)