package com.loki.gitresume.ui.login

data class LoginState(
    val email: String = "",
    val isEmailError: Boolean = false,
    val emailError: String = "",
    val password: String = "",
    val isPasswordError: Boolean = false,
    val passwordError: String = ""
)