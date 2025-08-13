package com.example.dinhngocthe.presentation.musicplayer

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dinhngocthe.R
import com.example.dinhngocthe.service.musicstate.MusicPlayerLibrary
import com.example.dinhngocthe.service.MusicService
import org.koin.androidx.compose.koinViewModel

@Composable
fun MusicPlayerScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: MusicPlayerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.processIntent(MusicPlayerIntent.LoadData)
    }

    val iconPlayPause = if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                MusicPlayerEvent.CloseMusic -> {
                    if (MusicPlayerLibrary.isActive()) {
                        MusicPlayerLibrary.stopMusic()
                    } else {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_CLOSE
                        }
                        context.startService(intent)
                    }
                    onBack()
                }

                MusicPlayerEvent.PlayPauseMusic -> {
                    if (MusicPlayerLibrary.isActive()) {
                        MusicPlayerLibrary.playPauseMusic()
                    } else {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_PLAY_PAUSE
                        }
                        context.startService(intent)
                    }
                }

                MusicPlayerEvent.NextMusic -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_NEXT
                    }
                    context.startService(intent)
                }

                MusicPlayerEvent.PreviousMusic -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PREVIOUS
                    }
                    context.startService(intent)
                }

                is MusicPlayerEvent.UpdateProgress -> {
                    if (MusicPlayerLibrary.isActive()) {
                        MusicPlayerLibrary.updateProgress(event.progress)
                    } else {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_SEEK_TO
                            putExtra("PROGRESS", event.progress)
                        }
                        context.startService(intent)
                    }
                }

                MusicPlayerEvent.StopUpdateProgress -> {
                    if (MusicPlayerLibrary.isActive()) {
                        MusicPlayerLibrary.stopUpdateProgress()
                    } else {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicService.ACTION_STOP_UPDATE_PROGRESS
                        }
                        context.startService(intent)
                    }
                }

                MusicPlayerEvent.Shuffle -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_SHUFFLE
                    }
                    context.startService(intent)
                }

                MusicPlayerEvent.Repeat -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_REPEAT
                    }
                    context.startService(intent)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding() + 10.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HeaderMusicPlayer(
                onBack = { onBack() },
                onCloseMusic = { viewModel.processIntent(MusicPlayerIntent.CloseMusic) }
            )

            MainMusicPlayer(
                progress = state.progress,
                duration = state.duration,
                coverArtUri = state.coverArtUri,
                songName = state.songName,
                singer = state.singer,
                iconPlayPause = iconPlayPause,
                enableShuffle = state.enableShuffle,
                enablePrevious = state.enablePrevious,
                enableNext = state.enableNext,
                enableRepeat = state.enableRepeat,
                isShuffle = state.isShuffle,
                isRepeat = state.isRepeat,
                onPlayPause = { viewModel.processIntent(MusicPlayerIntent.PlayPauseMusic) },
                onNextMusic = { viewModel.processIntent(MusicPlayerIntent.NextMusic) },
                onPreviousMusic = { viewModel.processIntent(MusicPlayerIntent.PreviousMusic) },
                onSeekTo = { viewModel.processIntent(MusicPlayerIntent.UpdateProgress(it)) },
                onChangeProgress = { viewModel.processIntent(MusicPlayerIntent.OnChangeProgress(it)) },
                onShuffle = { viewModel.processIntent(MusicPlayerIntent.Shuffle) },
                onRepeat = { viewModel.processIntent(MusicPlayerIntent.Repeat) }
            )
            //Log.d("MusicPlayer", state.progress.toString())
        }
    }
}

@Composable
fun HeaderMusicPlayer(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onCloseMusic: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 7.dp)
    ) {
        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = "Now Playing",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = { onCloseMusic() },
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Spacer(Modifier.size(20.dp))
}