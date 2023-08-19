package com.loki.gitresume.ui.repository

import com.loki.gitresume.domain.models.Repository

data class RepoUiState(
    val isLoading: Boolean = false,
    val repos: List<Repository> = emptyList(),
    val message: String = ""
)
