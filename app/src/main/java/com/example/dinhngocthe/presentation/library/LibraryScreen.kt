package com.example.dinhngocthe.presentation.library

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.Song
import com.example.dinhngocthe.presentation.permission.RequestAudioPermissionIfNeeded
import com.example.dinhngocthe.presentation.view.ChoosePlaylistDialog

@Composable
fun LibraryScreen(
    innerPadding: PaddingValues,
    navigateToPlaylist: () -> Unit
) {
    val context = LocalContext.current.applicationContext as Application
    val viewModel: LibraryViewModel = viewModel(
        factory = remember { LibraryViewModel.LibraryViewModelFactory(context) }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    RequestAudioPermissionIfNeeded(
        onPermissionGranted = {
            viewModel.processIntent(LibraryIntent.LoadData)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadData)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                LibraryEvent.NavigateToPlaylist -> navigateToPlaylist()
            }
        }
    }

    var songSource by remember { mutableStateOf("local") }
    lateinit var buttonLocalColor: Pair<Color, Color>
    lateinit var buttonRemoteColor: Pair<Color, Color>

    // Swap color when change songSource
    if (songSource == "local") {
        buttonLocalColor = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        buttonRemoteColor = MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        buttonRemoteColor = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        buttonLocalColor =
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }

    var expandedSongMenuIndex by remember { mutableIntStateOf(-1) }
    var showChoosePlaylistToAddSong by remember { mutableStateOf(false) }
    var selectedSongIndex by remember { mutableIntStateOf(-1) }

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
            onLocalClick = { songSource = "local" },
            onRemoteClick = { songSource = "remote" },
            localColor = buttonLocalColor,
            remoteColor = buttonRemoteColor
        )

        Spacer(Modifier.size(25.dp))

        MainLibrary(
            displayMode = songSource,
            localSongs = state.localSongs,
            remoteSongs = state.remoteSongs,
            expandedIndex = expandedSongMenuIndex,
            onShowMenu = { expandedSongMenuIndex = it },
            onDismissMenu = { expandedSongMenuIndex = -1 },
            addToPlaylist = {
                showChoosePlaylistToAddSong = true
                selectedSongIndex = it
            }
        )

        if (showChoosePlaylistToAddSong) {
            ChoosePlaylistDialog(
                playlists = state.playlists,
                onDismiss = { showChoosePlaylistToAddSong = false },
                onSelectPlaylist = {
                    val song = state.localSongs[selectedSongIndex]
                    //Log.d("Library Screen", song.name + it)
                    viewModel.processIntent(LibraryIntent.AddMusicToPlaylist(state.playlists[it].playlistId, song.songId))
                    showChoosePlaylistToAddSong = false
                },
                navigateToPlaylist = {
                    showChoosePlaylistToAddSong = false
                    viewModel.processIntent(LibraryIntent.NavigateToPlaylist)
                }
            )
        }
    }
}



