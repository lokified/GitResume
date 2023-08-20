package com.loki.gitresume.data.remote.api

import com.loki.gitresume.data.remote.response.RepositoryDto
import com.loki.gitresume.data.remote.response.UserDto
import com.loki.gitresume.util.Constants.USERNAME
import retrofit2.http.GET

interface GithubApi {

    /**
     * The endpoint gets list of repository
     */
    @GET("users/$USERNAME/repos")
    suspend fun getRepos(): List<RepositoryDto>

    /**
     * The endpoint gets the user profile
     */
    @GET("user")
    suspend fun getUserProfile(): UserDto
}