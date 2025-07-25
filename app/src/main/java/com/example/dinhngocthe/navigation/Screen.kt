package com.example.dinhngocthe.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object SignUpScreen : Screen("signup")
    object MyPlayListScreen : Screen("playlist")
}