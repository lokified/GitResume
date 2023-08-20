package com.loki.gitresume.data.remote.repository

import com.loki.gitresume.data.remote.api.GithubApi
import com.loki.gitresume.data.remote.mappers.toGitUser
import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.domain.repository.UserRepository
import com.loki.gitresume.util.Resource
import com.loki.gitresume.util.response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApi
): UserRepository {
    override suspend fun getUserProfile(): Flow<Resource<GitUser>> = response {
        api.getUserProfile().toGitUser()
    }
}