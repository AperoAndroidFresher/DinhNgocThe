package com.example.dinhngocthe.presentation.login

sealed interface LoginIntent {
    data class LoginClicked(val username: String, val password: String, val rememberMe: Boolean) : LoginIntent
    data object NavigateToSignUp: LoginIntent
}