package com.example.githubusers.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.data.local.room.FavoriteUserDao
import com.example.githubusers.data.remote.models.SearchUserResponse
import com.example.githubusers.data.remote.models.UserDetail
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
    private val apiResult = MediatorLiveData<Result<List<UserResponseItem>>>()
    private val userDetail = MediatorLiveData<Result<UserDetail>>()

    fun getListUsers(): LiveData<Result<List<UserResponseItem>>> {
        apiResult.value = Result.Loading
        val client = apiService.getListUser()
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(call: Call<List<UserResponseItem>>, response: Response<List<UserResponseItem>>) {
                if (response.isSuccessful) {
                    val userList = ArrayList<UserResponseItem>()
                    appExecutors.diskIO.execute {
                        response.body()?.forEach { article ->
                            val user = UserResponseItem(
                                article.login,
                                article.url,
                                article.avatarUrl,
                                article.htmlUrl,
                                article.id
                            )
                            userList.add(user)
                        }
                    }
                    apiResult.value = Result.Success(userList)
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                apiResult.value = Result.Error(t.message.toString())
            }
        })
//        val localData = favoriteUserDao.getUsers()
//        result.addSource(localData) { newData: List<FavoriteUserEntity> ->
//            result.value = Result.Success(newData)
//        }
        return apiResult
    }

    fun findUser(query: String): LiveData<Result<List<UserResponseItem>>> {
        apiResult.value = Result.Loading
        val client = apiService.getSearchUser(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                if (response.isSuccessful) {
                    apiResult.value = Result.Success(response.body()?.items as List<UserResponseItem>)
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                apiResult.value = Result.Error(t.message.toString())
            }
        })

        return apiResult
    }

    fun findUserByUrl(url: String): LiveData<Result<UserDetail>> {
        val username = url.split("https://api.github.com/users/")[1]
        userDetail.value = Result.Loading
        val client = apiService.getUserDetail(username)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                if (response.isSuccessful) {
                    userDetail.value = Result.Success(response.body() as UserDetail)
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                userDetail.value = Result.Error(t.message.toString())
            }
        })

        return userDetail
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getFavoriteUsers()
    }

    fun setFavoriteUser(user: FavoriteUserEntity) {
        appExecutors.diskIO.execute {
            favoriteUserDao.insertOneFavoriteUser(user)
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