package com.loki.gitresume.domain.repository

import com.loki.gitresume.domain.models.Repository
import com.loki.gitresume.util.Resource
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepos(): Flow<Resource<List<Repository>>>
}