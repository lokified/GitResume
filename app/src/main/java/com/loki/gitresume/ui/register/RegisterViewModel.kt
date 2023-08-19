package com.loki.gitresume.ui.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.loki.gitresume.util.isValidEmail
import com.loki.gitresume.util.isValidPassword
import com.loki.gitresume.util.passwordMatches

class RegisterViewModel: ViewModel() {

    var state = mutableStateOf(RegisterState())
        private set

    private val email
        get() = state.value.email
    private val password
        get() = state.value.password
    private val conPassword
        get() = state.value.conPassword


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
    }
}