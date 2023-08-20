package com.loki.gitresume.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.loki.gitresume.data.local.datastore.DataStoreStorage
import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.domain.repository.UserRepository
import com.loki.gitresume.ui.repository.RepositoryViewModel
import com.loki.gitresume.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    private val userRepository = mockk<UserRepository>()
    private val authRepository = mockk<AuthRepository>()
    private val dataStoreStorage = mockk<DataStoreStorage>()

    private val userProfile = mock<GitUser>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(
            userRepository = userRepository,
            authRepository = authRepository,
            dataStore = dataStoreStorage
        )
    }

    @Test
    fun `getUserProfile returns Loading`() = runTest {

        coEvery { userRepository.getUserProfile() } returns flowOf(Resource.Loading())

        viewModel.getUserProfile()

        advanceUntilIdle()

        // ensures it is loading before getting data
        assert(viewModel.isLoading.value)

    }

    @Test
    fun `getUserProfile returns GitUser`() = runTest {

        coEvery { userRepository.getUserProfile() } returns flowOf(Resource.Success(userProfile))

        viewModel.getUserProfile()

        advanceUntilIdle()

        // ensure it has return a user profile
        assert(viewModel.userProfile.value != null)
    }

    @Test
    fun `getUserProfile returns  error message`() = runTest {
        val errorMessage = "Something Went wrong"

        coEvery { userRepository.getUserProfile() } returns flowOf(Resource.Error(errorMessage))

        viewModel.getUserProfile()

        advanceUntilIdle()

        //ensure it returns an error message
        assert(viewModel.errorMessage.value.isNotEmpty())

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}