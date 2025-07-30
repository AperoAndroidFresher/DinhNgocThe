package com.example.dinhngocthe.presentation.signup

sealed interface SignUpEvent {
    data object BackToLogin : SignUpEvent
    data object SignUpSuccess : SignUpEvent
}
