package com.loki.gitresume.data.remote.api

import com.loki.gitresume.data.remote.response.RepositoryDto
import com.loki.gitresume.util.Constants.USERNAME
import retrofit2.http.GET

interface GithubApi {

    @GET("$USERNAME/repos")
    suspend fun getRepos(): List<RepositoryDto>
}