package com.example.dinhngocthe.presentation.login

sealed interface LoginIntent {
    data class LoginClicked(val username: String, val password: String) : LoginIntent
    data object NavigateToSignUp: LoginIntent
}