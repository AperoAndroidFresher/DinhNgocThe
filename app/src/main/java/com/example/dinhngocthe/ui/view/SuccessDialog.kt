package com.example.dinhngocthe.ui.view

import android.os.Message
import com.example.dinhngocthe.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun SuccessDialog(onDismiss: () -> Unit, message: String) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(25.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Load animation
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = 1
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Success!",
                    fontFamily = AppFonts.mainFontBold,
                    fontSize = 26.sp,
                    color = Color(0xFF0BA27D)
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    message,
                    fontFamily = AppFonts.mainFont,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SuccessDialog({true}, "Your information has been updated")
}
