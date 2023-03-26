package com.example.githubusers.data.sample

import com.example.githubusers.data.remote.models.UserResponseItem

class SampleGithubUserData {
    fun getUserResponseData(): Array<UserResponseItem> {
        return SampleUserResponseData.data
    }
}