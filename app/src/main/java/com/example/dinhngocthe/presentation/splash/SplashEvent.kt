package com.example.dinhngocthe.presentation.splash

sealed interface SplashEvent {
    data class NavigateToApp(val isLogged: Boolean) : SplashEvent
}