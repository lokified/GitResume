package com.loki.gitresume.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.gitresume.data.local.datastore.DataStoreStorage
import com.loki.gitresume.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreStorage
): ViewModel() {


    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepository.signOut()
            dataStore.saveLoggedIn(false)
            onLogout()
        }
    }
}