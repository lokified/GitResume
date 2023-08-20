package com.loki.gitresume.domain.repository

import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserProfile(): Flow<Resource<GitUser>>
}