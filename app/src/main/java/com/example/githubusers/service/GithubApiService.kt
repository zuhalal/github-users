package com.example.githubusers.service

import com.example.githubusers.models.UserResponseItem
import com.example.githubusers.models.SearchUserResponse
import com.example.githubusers.models.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @Headers("Authorization: token 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")
    @GET("/users")
    fun getListUser(): Call<List<UserResponseItem>>

    @Headers("Authorization: token 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")
    @GET("/search/users")
    fun getSearchUser(
        @Query("q") searchQuery: String
    ): Call<SearchUserResponse>

    @Headers("Authorization: token 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")
    @GET("/users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetail>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/followers")
    fun getListUserFollower(@Path("username") username: String): Call<List<UserResponseItem>>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/following")
    fun getListUserFollowing(@Path("username") username: String): Call<List<UserResponseItem>>
}
