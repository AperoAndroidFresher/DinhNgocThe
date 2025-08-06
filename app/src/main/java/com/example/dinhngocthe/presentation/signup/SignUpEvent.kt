package com.example.dinhngocthe.presentation.signup

sealed interface SignUpEvent {
    data object NavigateToLogin : SignUpEvent
    data object SignUpSuccess : SignUpEvent
}
