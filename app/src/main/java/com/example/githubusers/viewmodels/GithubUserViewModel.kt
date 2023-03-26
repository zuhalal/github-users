package com.example.githubusers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.GithubUserRepository
import com.example.githubusers.data.local.entity.FavoriteUserEntity

class GithubUserViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findAllUser() = repository.getListUsers()

    fun findUser(query: String) = repository.findUser(query)

    fun findUserByUrl(url: String) = repository.findUserByUrl(url)

    fun findAllFavoriteUser() = repository.getFavoriteUser()

    fun setFavoriteUser(user: FavoriteUserEntity) = repository.setFavoriteUser(user)

    fun deleteFavoriteUser(username: String) = repository.removeFavoriteUser(username)

    fun findAllUserFollower(url: String) = repository.findAllUserFollower(url)

    fun findAllUserFollowing(url: String) = repository.findAllUserFollowing(url)
}
