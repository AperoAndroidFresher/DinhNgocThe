package com.example.dinhngocthe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowInsetsControllerCompat
import com.example.dinhngocthe.ui.view.MainScreen
import com.example.dinhngocthe.ui.theme.DinhNgocTheTheme
import com.example.dinhngocthe.ui.view.MyPlaylistScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        setContent {
            DinhNgocTheTheme {
                Scaffold { innerPadding ->
//                    MainScreen(
//                        Modifier
//                            .background(Color(0xFFF5FAFF))
//                            .padding(innerPadding)
//                    )
                    MyPlaylistScreen(
                        Modifier
                            .background(colorResource(R.color.dark))
                            .padding(top = innerPadding.calculateTopPadding())
                    )
                }
            }
        }
    }

}
