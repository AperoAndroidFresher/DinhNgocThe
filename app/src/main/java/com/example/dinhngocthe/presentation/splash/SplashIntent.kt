package com.example.dinhngocthe.presentation.splash

sealed interface SplashIntent {
    data object CheckAutoLogin : SplashIntent
}