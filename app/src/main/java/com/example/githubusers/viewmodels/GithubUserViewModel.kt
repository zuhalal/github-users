package com.example.githubusers.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.models.ListUserResponseItem
import com.example.githubusers.models.SearchUserResponse
import com.example.githubusers.remote.RetrofitConfig
import com.example.githubusers.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserViewModel: ViewModel() {
    private val _listUserResponse = MutableLiveData<List<ListUserResponseItem>>()
    val listUserResponse: LiveData<List<ListUserResponseItem>> = _listUserResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "GithubUserViewModel"
    }


    init {
        findAllUser()
    }

    private fun findAllUser() {
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getListUser()
        client.enqueue(object : Callback<List<ListUserResponseItem>> {
            override fun onResponse(
                call: Call<List<ListUserResponseItem>>,
                response: Response<List<ListUserResponseItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listUserResponse.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun findUser(query: String) {
        _isLoading.value = true
        val client = RetrofitConfig.getGithubApiService().getSearchUser(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
//                    _restaurant.value = response.body()?.restaurant
//                    _listReview.value = response.body()?.restaurant?.customerReviews
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
}