package com.loki.gitresume.data.remote.repository

import com.loki.gitresume.data.remote.api.GithubApi
import com.loki.gitresume.data.remote.mappers.toRepository
import com.loki.gitresume.domain.models.Repository
import com.loki.gitresume.domain.repository.RepoRepository
import com.loki.gitresume.util.Resource
import com.loki.gitresume.util.response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val api: GithubApi
): RepoRepository {

    override suspend fun getRepos(): Flow<Resource<List<Repository>>> = response {
        api.getRepos().map { it.toRepository() }
    }
}