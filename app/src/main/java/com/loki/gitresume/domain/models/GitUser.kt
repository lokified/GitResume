package com.loki.gitresume.domain.models

import com.google.gson.annotations.SerializedName

data class GitUser(
    val avatarUrl: String?,
    val bio: String?,
    val followers: Int?,
    val following: Int?,
    val login: String?,
    val name: String?,
    val publicRepos: Int?
)
