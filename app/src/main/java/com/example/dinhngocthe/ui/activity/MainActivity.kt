package com.example.dinhngocthe.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.core.view.WindowInsetsControllerCompat
import com.example.dinhngocthe.navigation.BottomNavBar
import com.example.dinhngocthe.navigation.NavRoutes
import com.example.dinhngocthe.ui.theme.AppTheme
import com.example.dinhngocthe.ui.view.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }
            var splashVisible by remember { mutableStateOf(true) }
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme

            LaunchedEffect(Unit) {
                delay(2000)
                splashVisible = false
            }

            AppTheme(isDarkTheme) {
                if (splashVisible) {
                    SplashScreen()
                } else {
                    NavRoutes(
                        onChangeMode = { isDarkTheme = !isDarkTheme },
                        isDarkTheme
                    )
                }
            }
        }
    }
}
