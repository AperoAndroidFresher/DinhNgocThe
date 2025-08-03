package com.example.dinhngocthe.presentation.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

fun getRequiredAudioPermission(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        Manifest.permission.READ_MEDIA_AUDIO
    else
        Manifest.permission.READ_EXTERNAL_STORAGE
}

fun shouldRequestAudioPermission(context: Context): Boolean {
    val permission = getRequiredAudioPermission()
    return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
}

@Composable
fun RequestAudioPermissionIfNeeded(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val permission = getRequiredAudioPermission()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) onPermissionGranted()
        }
    )

    LaunchedEffect(Unit) {
        if (shouldRequestAudioPermission(context)) {
            launcher.launch(permission)
        }
    }
}