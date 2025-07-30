package com.example.dinhngocthe.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.example.dinhngocthe.R
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    goProfile: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding(), start = 15.dp, end = 15.dp)
    ) {
        IconButton(
            onClick = goProfile,
            modifier = Modifier.align(Alignment.End).size(50.dp).padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Go Profile"
            )
        }
        Text(
            text = "HOME SCREEN",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}