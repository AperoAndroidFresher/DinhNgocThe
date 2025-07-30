package com.example.dinhngocthe.presentation.signup

sealed interface SignUpIntent {
    data class UsernameChanged(val username: String) : SignUpIntent
    data class PasswordChanged(val password: String) : SignUpIntent
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent
    data class EmailChanged(val email: String) : SignUpIntent

    data object TogglePasswordVisible : SignUpIntent
    data object ToggleConfirmPasswordVisible : SignUpIntent

    data object SignUpClicked : SignUpIntent
    data object BackToLogin : SignUpIntent
}
