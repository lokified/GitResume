package com.loki.gitresume.data.remote.repository

import com.loki.gitresume.domain.models.Repository
import com.loki.gitresume.domain.repository.RepoRepository
import com.loki.gitresume.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock

class RepoRepositoryTest {

    companion object {
        fun mockGetRepositoryList(
            flowReturn: Flow<Resource<List<Repository>>>
        ) = object : RepoRepository {
            override suspend fun getRepos(): Flow<Resource<List<Repository>>> = flowReturn
        }
    }

    @Test
    fun `getRepos starts with loading returns Resource Loading` () = runBlocking {
        val repos = mock<List<Repository>>()

        val repoRepository = mockGetRepositoryList(
            flow {
                emit(Resource.Loading())
                emit(Resource.Success(repos))
            }
        )

        val result = repoRepository.getRepos().first()

        assert(result is Resource.Loading)
    }

    @Test
    fun `getRepos with data returns Resource Success` () = runBlocking {
        val repos = mock<List<Repository>>()

        val repoRepository = mockGetRepositoryList(
            flow {
                emit(Resource.Loading())
                emit(Resource.Success(repos))
            }
        )

        val result = repoRepository.getRepos().last()

        assert(result is Resource.Success && (result.data ?: false) != emptyList<List<Repository>>())
    }

    @Test
    fun `getRepos with error returns Resource Error` () = runBlocking {
        val repos = mock<List<Repository>>()

        val repoRepository = mockGetRepositoryList(
            flow {
                emit(Resource.Error(message = "An unexpected error occurred", data = repos))
            }
        )

        val result = repoRepository.getRepos().last()

        assert(result is Resource.Error && result.message == "An unexpected error occurred")
    }
}