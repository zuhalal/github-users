package com.example.githubusers.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.GithubUserRepository
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.data.remote.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val _listFollower = MutableLiveData<List<UserResponseItem>>()
    val listFollower: LiveData<List<UserResponseItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<UserResponseItem>>()
    val listFollowing: LiveData<List<UserResponseItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "GithubUserViewModel"
    }

    fun findAllUser() = repository.getListUsers()

    fun findUser(query: String) = repository.findUser(query)

    fun findUserByUrl(url: String) = repository.findUserByUrl(url)

    fun findAllFavoriteUser() = repository.getFavoriteUser()

    fun setFavoriteUser(user: FavoriteUserEntity) = repository.setFavoriteUser(user)

    fun deleteFavoriteUser(username: String) = repository.removeFavoriteUser(username)

    fun findAllUserFollower(url: String) {
        val username = url.split("https://api.github.com/users/")[1]
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getListUserFollower(username)
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findAllUserFollowing(url: String) {
        val username = url.split("https://api.github.com/users/")[1]
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getListUserFollowing(username)
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
