package com.loki.gitresume.ui.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.loki.gitresume.domain.models.Repository
import com.loki.gitresume.domain.repository.RepoRepository
import com.loki.gitresume.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RepositoryViewModel
    private val repository = mockk<RepoRepository>()

    private val repos = mock<List<Repository>>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = RepositoryViewModel(repository)
    }

    @Test
    fun `getRepository returns Loading`() = runTest {

        coEvery { repository.getRepos() } returns flowOf(Resource.Loading())

        viewModel.getRepository()

        advanceUntilIdle()

        // ensures it is loading before getting data
        assert(viewModel.uiState.value.isLoading)

    }

    @Test
    fun `getRepository returns list of repositories`() = runTest {

        coEvery { repository.getRepos() } returns flowOf(Resource.Success(repos))

        viewModel.getRepository()

        advanceUntilIdle()

        // ensure it has return a list of repositories
        assert(viewModel.uiState.value.repos.isNotEmpty())

    }

    @Test
    fun `getRepository returns  error message`() = runTest {
        val errorMessage = "Something Went wrong"

        coEvery { repository.getRepos() } returns flowOf(Resource.Error(errorMessage))

        viewModel.getRepository()

        advanceUntilIdle()

        //ensure it returns an error message
        assert(viewModel.uiState.value.message.isNotEmpty())

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}