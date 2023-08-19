package com.loki.gitresume.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.loki.gitresume.util.isValidEmail

class LoginViewModel: ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    private val email
        get() = state.value.email

    private val password
        get() = state.value.password


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
    }
}