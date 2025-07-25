package com.example.dinhngocthe.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.example.dinhngocthe.navigation.AppNavHost
import com.example.dinhngocthe.ui.theme.AppTheme
import com.example.dinhngocthe.ui.view.LoginScreen
import com.example.dinhngocthe.ui.view.MainScreen
import com.example.dinhngocthe.ui.view.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }
            var splashVisible by remember { mutableStateOf(true) }
            val navController = rememberNavController()
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme

            LaunchedEffect(Unit) {
                delay(2000)
                splashVisible = false
            }

            AppTheme(isDarkTheme) {
                if (splashVisible) {
                    SplashScreen()
                } else {
                    Scaffold { innerPadding ->
                        AppNavHost(
                            navHostController = navController,
                            innerPadding = innerPadding.calculateTopPadding()
                        )
                    }
                }
            }
        }
    }
}
