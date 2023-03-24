package com.example.githubusers.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubusers.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getUsers(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUsers(): LiveData<List<FavoriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favoriteUser: List<FavoriteUserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOneFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Update
    fun updateFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("DELETE FROM favorite_user")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE username = :username)")
    fun isUserFavorited(username: String): Boolean
}