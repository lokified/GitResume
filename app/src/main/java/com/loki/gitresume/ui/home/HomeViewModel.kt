package com.loki.gitresume.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.gitresume.data.local.datastore.DataStoreStorage
import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.domain.repository.ResumeRepository
import com.loki.gitresume.domain.repository.UserRepository
import com.loki.gitresume.util.DownloadStatus
import com.loki.gitresume.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val dataStore: DataStoreStorage,
    private val resumeRepository: ResumeRepository
): ViewModel() {

    private val _userProfile = mutableStateOf<GitUser?>(null)
    val userProfile = _userProfile

    private val  _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getUserProfile().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.value = HomeUiState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _userProfile.value = result.data
                    }
                    is Resource.Error -> {
                        _uiState.value = HomeUiState(
                           errorMessage = result.message ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun downloadResume() {

        viewModelScope.launch {
            resumeRepository.downloadResume{ status ->

                when(status) {
                    is DownloadStatus.OnDownloadSuccess -> {
                        _uiState.value = HomeUiState(
                            message = status.message
                        )
                    }
                    is DownloadStatus.OnDownloadStarted -> {
                        _uiState.value = HomeUiState(
                            message = status.message
                        )
                    }
                    is DownloadStatus.OnDownloadFailed -> {
                        _uiState.value = HomeUiState(
                            errorMessage = status.message
                        )
                    }
                }
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepository.signOut()
            dataStore.saveLoggedIn(false)
            onLogout()
        }
    }
}