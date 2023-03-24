package com.example.githubusers.data.remote.retrofit

import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.data.remote.models.SearchUserResponse
import com.example.githubusers.data.remote.models.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @Headers(
        "Authorization: token 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"
    )
    @GET("/users")
    fun getListUser(): Call<List<UserResponseItem>>

    @Headers(
        "Authorization: token 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"
    )
    @GET("/search/users")
    fun getSearchUser(
        @Query("q") searchQuery: String
    ): Call<SearchUserResponse>

    @Headers(
        "Authorization: token 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"
    )
    @GET("/users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetail>

    @Headers(
        "Authorization: token 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"
    )
    @GET("/users/{username}/followers")
    fun getListUserFollower(@Path("username") username: String): Call<List<UserResponseItem>>

    @Headers(
        "Authorization: token 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"
    )
    @GET("/users/{username}/following")
    fun getListUserFollowing(@Path("username") username: String): Call<List<UserResponseItem>>
}
