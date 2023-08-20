package com.loki.gitresume.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.gitresume.data.local.datastore.DataStoreStorage
import com.loki.gitresume.domain.models.GitUser
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.domain.repository.UserRepository
import com.loki.gitresume.ui.repository.RepoUiState
import com.loki.gitresume.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val dataStore: DataStoreStorage
): ViewModel() {

    private val _userProfile = mutableStateOf<GitUser?>(null)
    val userProfile = _userProfile

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getUserProfile().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        _userProfile.value = result.data
                        isLoading.value = false
                    }
                    is Resource.Error -> {
                        errorMessage.value = result.message ?: "Something went wrong"
                        isLoading.value = false
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