package com.example.githubusers.data.sample

import com.example.githubusers.data.remote.models.UserResponseItem

object SampleUserResponseData {
    val data: Array<UserResponseItem> = arrayOf(
        UserResponseItem(
            "zuhalal",
            "https://api.github.com/users/zuhalal",
            "https://avatars.githubusercontent.com/u/74417769?v=4",
            "https://github.com/zuhalal"
        ),
        UserResponseItem(
            "mojombo",
            "https://api.github.com/users/mojombo",
            "https://avatars.githubusercontent.com/u/1?v=4",
            "https://github.com/mojombo"
        ),
        UserResponseItem(
            "defunkt",
            "https://api.github.com/users/defunkt",
            "https://avatars.githubusercontent.com/u/2?v=4",
            "https://github.com/defunkt"
        )
    )
}