package com.loki.gitresume.ui.forgot_password

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.loki.gitresume.util.isValidEmail

class ForgotPasswordViewModel: ViewModel() {

    var state = mutableStateOf(ForgotPasswordState())
        private set

    private val email
        get() = state.value.email

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue.trim())
        if (email.isValidEmail()) {
            state.value = state.value.copy(emailError = "", isEmailError = false)
        }
    }

    fun sendResetLink(onSuccess: () -> Unit) {
        if (!email.isValidEmail()) {
            state.value = state.value.copy(
                emailError = "Email is not valid",
                isEmailError = true
            )
            return
        }
        onSuccess()
    }
}