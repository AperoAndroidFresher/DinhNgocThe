package com.example.dinhngocthe.presentation.signup

data class SignUpState(
    val isLoading: Boolean = false,

    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null
)
