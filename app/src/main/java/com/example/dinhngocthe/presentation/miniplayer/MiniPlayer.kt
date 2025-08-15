package com.example.dinhngocthe.presentation.miniplayer

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dinhngocthe.R
import com.example.dinhngocthe.service.musicstate.MusicPlayerLibrary
import com.example.dinhngocthe.service.MusicService
import com.example.dinhngocthe.utils.formatDuration
import org.koin.androidx.compose.koinViewModel

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    navigateToMusicPlayerScreen: () -> Unit,
    viewModel: MiniPlayerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.processIntent(MiniPlayerIntent.LoadData)
    }

    val icPlayPause = if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                MiniPlayerEvent.PlayPauseMusic -> {
                    if (MusicPlayerLibrary.isActive()) {
                        MusicPlayerLibrary.playPauseMusic()
                    } else {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_PLAY_PAUSE
                        }
                        context.startService(intent)
                    }
                }

                MiniPlayerEvent.CloseMusic -> {
                    if (MusicPlayerLibrary.isActive()) {
                        MusicPlayerLibrary.stopMusic()
                    } else {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_CLOSE
                        }
                        context.startService(intent)
                    }
                }
            }
        }
    }

    if (state.isActive) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable { navigateToMusicPlayerScreen() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            viewModel.processIntent(MiniPlayerIntent.PlayPauseMusic)
                        },
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(10.dp),
                            painter = painterResource(icPlayPause),
                            contentDescription = "Press to play or pause music",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Text(
                        text = state.songName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 14.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                        text = formatDuration((state.duration * state.progress).toLong()),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 14.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )

                    IconButton(
                        onClick = {
                            viewModel.processIntent(MiniPlayerIntent.CloseMusic)
                        },
                        modifier = Modifier.padding(bottom = 3.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(7.dp),
                            painter = painterResource(R.drawable.ic_close_music),
                            contentDescription = "Press to close music",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).height(2.5.dp),
                progress = { state.progress },
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
            )
        }
    }
}
