package com.example.githubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubusers.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getUsers(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_user where is_favorite = 1")
    fun getFavoriteUsers(): LiveData<List<FavoriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favoriteUser: List<FavoriteUserEntity>)

    @Update
    fun updateFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("DELETE FROM favorite_user WHERE is_favorite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :username AND is_favorite = 1)")
    fun isUserFavorited(username: String): Boolean
}