package com.loki.gitresume.data.remote.mappers

import com.loki.gitresume.data.remote.response.RepositoryDto
import com.loki.gitresume.data.remote.response.UserDto
import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.domain.models.Repository

fun RepositoryDto.toRepository(): Repository {
    return Repository(
        id, name, url, description, topics
    )
}

fun UserDto.toGitUser(): GitUser {
    return GitUser(
        login = login,
        followers = followers,
        name = name,
        avatarUrl = avatarUrl,
        bio = bio,
        publicRepos = publicRepos,
        following = following
    )
}