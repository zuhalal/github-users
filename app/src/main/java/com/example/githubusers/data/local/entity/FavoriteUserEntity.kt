package com.example.githubusers.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName="favorite_user")
@Parcelize
data class FavoriteUserEntity  (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username: String = "",

    @ColumnInfo(name="url")
    val url: String,

    @ColumnInfo(name="avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name="html_url")
    val htmlUrl: String,

    @ColumnInfo(name="is_favorite")
    var isFavorite: Boolean,
): Parcelable