package com.example.dinhngocthe.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowInsetsControllerCompat
import com.example.dinhngocthe.presentation.navigation.NavRoutes
import com.example.dinhngocthe.presentation.theme.AppTheme
import com.example.dinhngocthe.presentation.view.SplashScreen
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

