package com.example.githubusers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String = "",
    val name: String = "",
    val avatar: String = "",
    val city: String = "",
    val follower: Int = 0,
    val following: Int = 0,
    val company: String = "",
    val location: String = "",
    val repository: String = ""
): Parcelable