package com.loki.gitresume.ui.home

data class HomeUiState(
    val isLoading: Boolean = false,
    var message: String = "",
    var errorMessage: String = ""
)