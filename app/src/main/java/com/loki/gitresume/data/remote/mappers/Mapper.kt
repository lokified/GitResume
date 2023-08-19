package com.loki.gitresume.data.remote.mappers

import com.loki.gitresume.data.remote.response.RepositoryDto
import com.loki.gitresume.domain.models.Repository

fun RepositoryDto.toRepository(): Repository {
    return Repository(
        id, name, url, description, topics
    )
}