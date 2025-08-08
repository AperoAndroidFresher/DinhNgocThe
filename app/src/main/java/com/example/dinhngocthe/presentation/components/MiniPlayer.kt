package com.example.dinhngocthe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.R
import com.example.dinhngocthe.presentation.theme.AppTheme
import com.example.dinhngocthe.utils.formatDuration

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    progress: Float,
    songName: String,
    duration: Long,
    onPlayPause: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(7.dp))
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { progress },
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onPlayPause() },
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp),
                        painter = painterResource(R.drawable.ic_play),
                        contentDescription = "Press to play or pause music",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = songName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 120.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDuration(duration),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                IconButton(
                    onClick = { onClose() }
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp),
                        painter = painterResource(R.drawable.ic_close_music),
                        contentDescription = "Press to close music",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MiniPlayerPrevLight() {
    AppTheme(false) {
        MiniPlayer(
            modifier = Modifier,
            progress = 0.5f,
            songName = "Chẳng phải phép màu hi hi hi hẹ hẹ hẹ ẹ ẹ ẹ",
            duration = 240_000L,
            onPlayPause = {},
            onClose = {}
        )
    }
}

@Preview
@Composable
private fun MiniPlayerPrevDark() {
    AppTheme(true) {
        MiniPlayer(
            modifier = Modifier,
            progress = 0.5f,
            songName = "Chẳng phải phép màu hi hi hi hẹ hẹ hẹ ẹ ẹ ẹ",
            duration = 240_000L,
            onPlayPause = {},
            onClose = {}
        )
    }
}