package com.example.dinhngocthe.presentation.login

sealed interface LoginIntent {
    data class UsernameChanged(val username: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data object TogglePasswordVisible: LoginIntent
    data class RememberMeChecked(val checked: Boolean) : LoginIntent
    data object LoginClicked : LoginIntent
    data object NavigateToSignUp: LoginIntent
}