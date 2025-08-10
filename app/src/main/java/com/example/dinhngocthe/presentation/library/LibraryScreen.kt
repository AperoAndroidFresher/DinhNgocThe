package com.example.dinhngocthe.presentation.library

import android.content.Intent
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dinhngocthe.data.local.entities.SongSource
import com.example.dinhngocthe.presentation.permission.RequestAudioPermissionIfNeeded
import com.example.dinhngocthe.presentation.components.ChoosePlaylistDialog
import com.example.dinhngocthe.service.MusicService
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreen(
    innerPadding: PaddingValues,
    navigateToPlaylist: () -> Unit,
    viewModel: LibraryViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    RequestAudioPermissionIfNeeded(
        onPermissionGranted = {
            viewModel.processIntent(LibraryIntent.LoadData)
        }
    )

    var songSource by remember { mutableStateOf(SongSource.LOCAL) }
    lateinit var buttonLocalColor: Pair<Color, Color>
    lateinit var buttonRemoteColor: Pair<Color, Color>

    // Swap color when change songSource
    if (songSource == SongSource.LOCAL) {
        buttonLocalColor = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        buttonRemoteColor = MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        buttonRemoteColor = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        buttonLocalColor =
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }

    var songMenuIndex by remember { mutableIntStateOf(-1) }
    var isInsertToPlaylistDialogVisible by remember { mutableStateOf(false) }
    var selectedSongIdToAdd by remember { mutableLongStateOf(-1) }

    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadData)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                LibraryEvent.NavigateToPlaylist -> navigateToPlaylist()
                is LibraryEvent.PlayMusic -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_START
                        putExtra("SONG_ID", event.currentSongId)
                        putExtra("SONG_IDS", ArrayList<Long>(event.songIds))
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    } else {
                        context.startService(intent)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        HeaderLibrary(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onLocalClick = { songSource = SongSource.LOCAL },
            onRemoteClick = { songSource = SongSource.REMOTE },
            localColor = buttonLocalColor,
            remoteColor = buttonRemoteColor
        )

        MainLibrary(
            songSource = songSource,
            localSongs = state.localSongs,
            remoteSongs = state.remoteSongs,
            expandedIndex = songMenuIndex,
            isLoadingRemoteSongs = state.isLoadingRemoteSongs,
            remoteError = state.remoteError,
            currentSongId = state.currentSongId,
            currentPlaySourceName = state.currentPlaySourceName,
            modifier = Modifier,
            onShowMenu = { songMenuIndex = it },
            onDismissMenu = { songMenuIndex = -1 },
            onInsertToPlaylist = {
                isInsertToPlaylistDialogVisible = true
                selectedSongIdToAdd = it
            },
            reload = { viewModel.processIntent(LibraryIntent.LoadData) },
            viewOffline = { viewModel.processIntent(LibraryIntent.ViewOffline) },
            onPlayMusic = { songId, sourceName, songIds ->
                viewModel.processIntent(LibraryIntent.PlayMusic(songId, songIds, sourceName))
            }
        )

        if (isInsertToPlaylistDialogVisible) {
            ChoosePlaylistDialog(
                playlists = state.playlists,
                onDismiss = { isInsertToPlaylistDialogVisible = false },
                onSelectPlaylist = {
                    //Log.d("Library Screen", song.name + it)
                    viewModel.processIntent(LibraryIntent.AddMusicToPlaylist(state.playlists[it].playlistId, selectedSongIdToAdd))
                    isInsertToPlaylistDialogVisible = false
                },
                navigateToPlaylist = {
                    isInsertToPlaylistDialogVisible = false
                    viewModel.processIntent(LibraryIntent.NavigateToPlaylist)
                }
            )
        }
    }
}



