package com.example.dinhngocthe.presentation.login

import android.util.Log

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
    data object NavigateToSignUp : LoginEvent
    data class ShowError(val error: String) : LoginEvent
}