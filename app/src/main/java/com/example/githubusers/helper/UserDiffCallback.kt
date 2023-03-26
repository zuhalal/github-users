package com.example.githubusers.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubusers.data.remote.models.UserResponseItem

class UserDiffCallback(
    private val mOldUserList: List<UserResponseItem>,
    private val mNewUserList: List<UserResponseItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].login == mNewUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mNewUserList[newItemPosition]
        return oldEmployee.htmlUrl == newEmployee.htmlUrl && oldEmployee.avatarUrl == newEmployee.avatarUrl && oldEmployee.url == newEmployee.url
    }
}