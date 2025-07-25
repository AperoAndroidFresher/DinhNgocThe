package com.example.dinhngocthe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dinhngocthe.ui.view.LoginScreen
import com.example.dinhngocthe.ui.view.SignUpScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    innerPadding: Dp
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                innerPadding = innerPadding,
                onSignUp = {
                    navHostController.navigate(Screen.SignUpScreen.route)
                }
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                innerPadding = innerPadding,
                signUpSuccess = {
                    navHostController.navigate(Screen.LoginScreen.route)
                }
            )
        }
    }
}
