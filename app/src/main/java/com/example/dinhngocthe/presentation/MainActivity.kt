package com.example.dinhngocthe.presentation

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.dinhngocthe.presentation.navigation.NavRoutes
import com.example.dinhngocthe.presentation.theme.AppTheme
import com.example.dinhngocthe.presentation.view.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
        }

        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }
            var splashVisible by remember { mutableStateOf(true) }
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme

            LaunchedEffect(Unit) {
                delay(2000)
                splashVisible = false
                requestAudioPermissionIfNeeded()
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

    private fun requestAudioPermissionIfNeeded() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            android.Manifest.permission.READ_MEDIA_AUDIO
        else
            android.Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(permission)
        }
    }

}

