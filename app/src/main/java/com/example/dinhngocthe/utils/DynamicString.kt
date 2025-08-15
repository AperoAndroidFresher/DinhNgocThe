package com.example.dinhngocthe.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@SuppressLint("LocalContextResourcesRead")
@Composable
fun dynamicString(name: String): String {
    val context = LocalContext.current
    val resId = remember(name) {
        context.resources.getIdentifier(name, "string", context.packageName)
    }
    return stringResource(id = resId)
}