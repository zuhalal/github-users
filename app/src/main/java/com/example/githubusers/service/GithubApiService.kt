package com.example.githubusers.service

import com.example.githubusers.models.UserResponseItem
import com.example.githubusers.models.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users")
    fun getListUser(): Call<List<UserResponseItem>>

    @GET("/search/users")
    fun getSearchUser(
        @Query("q") searchQuery: String
    ): Call<SearchUserResponse>
}
