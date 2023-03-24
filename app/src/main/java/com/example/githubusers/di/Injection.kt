package com.example.githubusers.di

import android.content.Context
import com.example.githubusers.data.FavoriteUserRepository
import com.example.githubusers.data.local.room.FavoriteUserDatabase
import com.example.githubusers.data.remote.retrofit.RetrofitConfig
import com.example.githubusers.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = RetrofitConfig.getGithubApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(apiService, dao, appExecutors)
    }
}