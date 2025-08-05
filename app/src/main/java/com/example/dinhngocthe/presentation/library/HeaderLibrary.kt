package com.example.dinhngocthe.presentation.library

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderLibrary(
    modifier: Modifier = Modifier,
    onLocalClick: () -> Unit,
    onRemoteClick: () -> Unit,
    localColor: Pair<Color, Color>,
    remoteColor: Pair<Color, Color>
) {
    Text(
        text = "Library",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )

    Spacer(Modifier.size(30.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 45.dp, end = 45.dp)
    ) {
        Button(
            onClick = onLocalClick,
            modifier = Modifier
                .weight(1f)
                .height(45.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = localColor.first,
                contentColor = localColor.second
            )
        ) {
            Text(
                "Local",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
        }

        Spacer(Modifier.size(30.dp))

        Button(
            onClick = onRemoteClick,
            modifier = Modifier
                .weight(1f)
                .height(45.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = remoteColor.first,
                contentColor = remoteColor.second
            )
        ) {
            Text(
                "Remote",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
        }
    }
}