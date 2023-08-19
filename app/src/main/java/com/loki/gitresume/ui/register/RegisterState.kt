package com.loki.gitresume.ui.register

data class RegisterState(
    val email: String = "",
    val isEmailError: Boolean = false,
    val emailError: String = "",
    val password: String = "",
    val isPasswordError: Boolean = false,
    val passwordError: String = "",
    val conPassword: String = "",
    val isConPasswordError: Boolean = false,
    val conPasswordError: String = ""
)