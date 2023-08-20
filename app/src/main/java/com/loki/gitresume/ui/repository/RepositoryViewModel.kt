package com.loki.gitresume.ui.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.gitresume.domain.repository.RepoRepository
import com.loki.gitresume.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repoRepository: RepoRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RepoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getRepository()
    }

    fun getRepository() {
        viewModelScope.launch {
            repoRepository.getRepos().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.value = RepoUiState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _uiState.value = RepoUiState(
                            repos = result.data!!
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = RepoUiState(
                            message = result.message ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }
}