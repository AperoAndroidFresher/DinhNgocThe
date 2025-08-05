package com.example.dinhngocthe.presentation.signup

import com.example.dinhngocthe.data.room.entities.User

sealed interface SignUpIntent {
    data class SignUpClicked(
        val username: String,
        val password: String,
        val confirmPassword: String,
        val email: String
    ) : SignUpIntent
    data object BackToLogin : SignUpIntent
}
