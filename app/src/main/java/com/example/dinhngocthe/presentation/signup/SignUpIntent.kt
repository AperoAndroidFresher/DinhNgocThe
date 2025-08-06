package com.example.dinhngocthe.presentation.signup

sealed interface SignUpIntent {
    data class SignUpClicked(
        val username: String,
        val password: String,
        val confirmPassword: String,
        val email: String
    ) : SignUpIntent
    data object NavigateToLogin : SignUpIntent
}
