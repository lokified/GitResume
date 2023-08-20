package com.loki.gitresume.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val bio: String?,
    val followers: Int?,
    val following: Int?,
    val id: Int?,
    val location: String?,
    val login: String?,
    val name: String?,
    @SerializedName("public_repos")
    val publicRepos: Int?
)
