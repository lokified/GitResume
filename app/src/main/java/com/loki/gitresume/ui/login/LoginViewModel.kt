package com.loki.gitresume.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.loki.gitresume.data.local.datastore.DataStoreStorage
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.util.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreStorage
): ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    private val email
        get() = state.value.email

    private val password
        get() = state.value.password

    val isLoggingIn = mutableStateOf(true)
    var isLoading = mutableStateOf(false)
    var message = mutableStateOf("")

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue.trim())
        if (email.isValidEmail()) {
            state.value = state.value.copy(emailError = "", isEmailError = false)
        }
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue.trim())
        if (password.isNotBlank()) {
            state.value = state.value.copy(passwordError = "", isPasswordError = false)
        }
    }

    fun login(navigateToHome: () -> Unit) {

        message.value = ""

        if (!email.isValidEmail()) {
            state.value = state.value.copy(
                emailError = "Email is not valid",
                isEmailError = true
            )
            return
        }

        if (password.isBlank()) {
            state.value = state.value.copy(
                passwordError = "Password cannot be empty",
                isPasswordError = true
            )
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true
                authRepository.authenticate(
                    email, password
                )
                isLoading.value = false
                dataStore.saveLoggedIn(true)
                navigateToHome()
            } catch (e: FirebaseException) {
                isLoading.value = false
                message.value = e.message ?: "Something went wrong"
            }
        }
    }

    fun onAppStart(openHomeScreen: () -> Unit) {
        viewModelScope.launch {
            dataStore.getLoggedIn().collect { loggedIn ->
                if (loggedIn) {
                    openHomeScreen()
                }
            }
        }
    }
}