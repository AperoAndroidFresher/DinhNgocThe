package com.example.dinhngocthe

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
import com.example.dinhngocthe.presentation.splash.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }
            var isSplash by remember { mutableStateOf(true) }
            var isLogged by remember { mutableStateOf(false) }
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme

            val destination = receiverIntent()

            AppTheme(isDarkTheme) {
                if (isSplash) {
                    SplashScreen(
                        navigateToApp = {
                            isLogged = it
                            isSplash = false
                        }
                    )
                } else {
                    NavRoutes(
                        onChangeMode = { isDarkTheme = !isDarkTheme },
                        isDarkTheme = isDarkTheme,
                        isLogged = isLogged
                    )
                }
            }
        }
    }

    private fun receiverIntent(): String {
        return intent.getStringExtra("DESTINATION") ?: ""
    }
}