package com.loki.gitresume.ui.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.util.isValidEmail
import com.loki.gitresume.util.isValidPassword
import com.loki.gitresume.util.passwordMatches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state = mutableStateOf(RegisterState())
        private set

    private val email
        get() = state.value.email
    private val password
        get() = state.value.password
    private val conPassword
        get() = state.value.conPassword

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
        if (password.isValidPassword()) {
            state.value = state.value.copy(passwordError = "", isPasswordError = false)
        }
    }

    fun onConPasswordChange(newValue: String) {
        state.value = state.value.copy(conPassword = newValue.trim())
        if (conPassword.passwordMatches(password)) {
            state.value = state.value.copy(conPasswordError = "", isConPasswordError = false)
        }
    }

    fun register(onRegister: () -> Unit) {

        message.value = ""

        if (!email.isValidEmail()) {
            state.value = state.value.copy(
                emailError = "Email is not valid",
                isEmailError = true
            )
            return
        }

        if (!password.isValidPassword()) {
            state.value = state.value.copy(
                passwordError = "Password is not valid",
                isPasswordError = true
            )
            return
        }

        if (!conPassword.passwordMatches(password)) {
            state.value = state.value.copy(
                conPasswordError = "Password does not match",
                isConPasswordError = true
            )
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true
                authRepository.createAccount(
                    email, password
                )
                isLoading.value = false
                onRegister()
            } catch (e: FirebaseException) {
                isLoading.value = false
                message.value = e.message ?: "Something went wrong"
            }
        }
    }
}