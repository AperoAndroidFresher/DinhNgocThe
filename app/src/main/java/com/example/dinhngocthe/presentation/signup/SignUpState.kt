package com.example.dinhngocthe.presentation.signup

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",

    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null
)
