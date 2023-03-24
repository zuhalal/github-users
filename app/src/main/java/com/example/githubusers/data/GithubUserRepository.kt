package com.example.githubusers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.data.local.room.FavoriteUserDao
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.data.remote.retrofit.GithubApiService
import com.example.githubusers.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserRepository private constructor (
    private val apiService: GithubApiService,
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteUserEntity>>>()

    fun getListUsers(): LiveData<Result<List<FavoriteUserEntity>>> {
        result.value = Result.Loading
        val client = apiService.getListUser()
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(call: Call<List<UserResponseItem>>, response: Response<List<UserResponseItem>>) {
                if (response.isSuccessful) {
                    val userList = ArrayList<FavoriteUserEntity>()
                    appExecutors.diskIO.execute {
                        response.body()?.forEach { article ->
                            val isBookmarked = favoriteUserDao.isUserFavorited(article.login)
                            val user = FavoriteUserEntity(
                                article.login,
                                article.url,
                                article.avatarUrl,
                                article.htmlUrl,
                                isBookmarked
                            )
                            userList.add(user)
                        }
                        favoriteUserDao.deleteAll()
                        favoriteUserDao.insertFavoriteUser(userList)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = favoriteUserDao.getUsers()
        result.addSource(localData) { newData: List<FavoriteUserEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getFavoriteUsers()
    }

    fun setFavoriteUser(user: FavoriteUserEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            user.isFavorite = bookmarkState
            favoriteUserDao.updateFavoriteUser(user)
        }
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: GithubApiService,
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, favoriteUserDao, appExecutors)
            }.also { instance = it }
    }
}