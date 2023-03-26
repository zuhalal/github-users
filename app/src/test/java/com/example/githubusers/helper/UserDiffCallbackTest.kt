package com.example.githubusers.helper

import com.example.githubusers.data.remote.models.UserResponseItem
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

internal class UserDiffCallbackTest {
    private lateinit var userDiffCallback: UserDiffCallback
    private val dummyUser = UserResponseItem(
        "zuhalal",
        "https://api.github.com/users/zuhalal",
        "https://avatars.githubusercontent.com/u/74417769?v=4",
        "https://github.com/zuhalal"
    )

    private val dummyUserTwo = UserResponseItem(
        "zuhalal",
        "https://api.github.com/users/zuhalalsecond",
        "https://avatars.githubusercontent.com/u/74417769?v=4",
        "https://github.com/zuhalalsecond"
    )

    @Before
    fun before() {
        userDiffCallback = Mockito.mock(UserDiffCallback::class.java)
    }

    @Test
    fun getOldListSize() {
        userDiffCallback = UserDiffCallback(listOf(), listOf(dummyUser))
        val oldSize = userDiffCallback.oldListSize
        Assert.assertEquals(0, oldSize)
    }

    @Test
    fun getNewListSize() {
        userDiffCallback = UserDiffCallback(listOf(), listOf(dummyUser))
        val newSize = userDiffCallback.newListSize
        Assert.assertEquals(1, newSize)
    }

    @Test
    fun areItemsTheSame() {
        userDiffCallback = UserDiffCallback(listOf(dummyUser), listOf(dummyUserTwo))
        val isItemSame = userDiffCallback.areItemsTheSame(0, 0)
        Assert.assertEquals(true, isItemSame)
    }

    @Test
    fun areContentsTheNotSame() {
        userDiffCallback = UserDiffCallback(listOf(dummyUser), listOf(dummyUserTwo))
        val isContentSame = userDiffCallback.areContentsTheSame(0, 0)
        Assert.assertEquals(false, isContentSame)
    }

    @Test
    fun areContentsTheSame() {
        userDiffCallback = UserDiffCallback(listOf(dummyUser), listOf(dummyUser))
        val isContentSame = userDiffCallback.areContentsTheSame(0, 0)
        Assert.assertEquals(true, isContentSame)
    }
}