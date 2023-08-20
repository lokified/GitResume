package com.loki.gitresume.data.remote.repository

import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.domain.repository.UserRepository
import com.loki.gitresume.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock

class UserRepositoryTest {

    companion object {
        fun mockGetUserProfile(
            flowReturn: Flow<Resource<GitUser>>
        ) = object : UserRepository {
            override suspend fun getUserProfile(): Flow<Resource<GitUser>> = flowReturn
        }
    }

    @Test
    fun `getUserProfile starts with loading returns Resource Loading` () = runBlocking {
        val profile = mock<GitUser>()

        val userRepository = mockGetUserProfile(
            flow {
                emit(Resource.Loading())
                emit(Resource.Success(profile))
            }
        )

        val result = userRepository.getUserProfile().first()

        assert(result is Resource.Loading)
    }

    @Test
    fun `getUserProfile with data returns Resource Success` () = runBlocking {
        val profile = mock<GitUser>()

        val userRepository = mockGetUserProfile(
            flow {
                emit(Resource.Loading())
                emit(Resource.Success(profile))
            }
        )

        val result = userRepository.getUserProfile().last()

        assert(result is Resource.Success)
    }

    @Test
    fun `getUserProfile with error returns Resource Error` () = runBlocking {

        val errorMessage = "An unexpected error occurred"

        val userRepository = mockGetUserProfile(
            flow {
                emit(Resource.Error(errorMessage))
            }
        )

        val result = userRepository.getUserProfile().last()

        assert(result is Resource.Error && result.message == "An unexpected error occurred")
    }
}