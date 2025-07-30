package com.example.dinhngocthe.presentation.profile

sealed interface ProfileEvent {
    data object ChangeTheme : ProfileEvent
    data object ShowSuccessDialog : ProfileEvent
}
