package com.loki.gitresume.ui.forgot_password

data class ForgotPasswordState(
    val email: String = "",
    val emailError: String = "",
    val isEmailError: Boolean = false
)
