package com.loki.gitresume.ui.forgot_password

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.loki.gitresume.domain.repository.AuthRepository
import com.loki.gitresume.util.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    var state = mutableStateOf(ForgotPasswordState())
        private set

    private val email
        get() = state.value.email

    var isLoading = mutableStateOf(false)
    var message = mutableStateOf("")

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

        viewModelScope.launch {
            try {
                isLoading.value = true
                authRepository.sendRecoveryEmail(email)
                isLoading.value = false
                onSuccess()
            } catch (e: FirebaseException) {
                isLoading.value = false
                message.value = e.message ?: "Something went wrong"
            }
        }
    }
}