package com.example.githubusers.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.models.UserResponseItem
import com.example.githubusers.models.SearchUserResponse
import com.example.githubusers.models.UserDetail
import com.example.githubusers.remote.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserViewModel: ViewModel() {
    private val _listUserResponse = MutableLiveData<List<UserResponseItem>>()
    val listUserResponse: LiveData<List<UserResponseItem>> = _listUserResponse

    private val _listFollower = MutableLiveData<List<UserResponseItem>>()
    val listFollower: LiveData<List<UserResponseItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<UserResponseItem>>()
    val listFollowing: LiveData<List<UserResponseItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    companion object{
        private const val TAG = "GithubUserViewModel"
    }

    init {
        findAllUser()
    }

    fun findAllUser() {
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getListUser()
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listUserResponse.value = response.body()
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

    fun findUser(query: String) {
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getSearchUser(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUserResponse.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findUserByUrl(url: String) {
        val username = url.split("https://api.github.com/users/")[1]
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

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
