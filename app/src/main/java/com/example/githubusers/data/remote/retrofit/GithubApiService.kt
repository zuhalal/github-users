package com.example.githubusers.data.remote.retrofit

import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.data.remote.models.SearchUserResponse
import com.example.githubusers.data.remote.models.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users")
    fun getListUser(): Call<List<UserResponseItem>>

    @GET("/search/users")
    fun getSearchUser(
        @Query("q") searchQuery: String
    ): Call<SearchUserResponse>

    @GET("/users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetail>

    @GET("/users/{username}/followers")
    fun getListUserFollower(@Path("username") username: String): Call<List<UserResponseItem>>

    @GET("/users/{username}/following")
    fun getListUserFollowing(@Path("username") username: String): Call<List<UserResponseItem>>
}
