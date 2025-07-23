package com.example.dinhngocthe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dinhngocthe.ui.view.MainScreen
import com.example.dinhngocthe.ui.theme.DinhNgocTheTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DinhNgocTheTheme {
                MainScreen()
            }
        }
    }
}
