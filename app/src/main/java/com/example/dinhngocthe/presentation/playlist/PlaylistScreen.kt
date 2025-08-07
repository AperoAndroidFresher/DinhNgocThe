package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinhngocthe.R
import com.example.dinhngocthe.utils.transformSongWithPlaylistIdToSong
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyPlaylistScreen(
    innerPadding: PaddingValues,
    viewModel: PlaylistViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(PlaylistIntent.LoadData)
    }

    //Playlist state
    var selectedPlaylistIndex by remember { mutableIntStateOf(-1) }
    var isInsertPlaylistDialogVisible by remember { mutableStateOf(false) }
    var playlistMenuIndex by remember { mutableIntStateOf(-1) }
    var renamingPlaylistIndex by remember { mutableIntStateOf(-1) }
    var isRenamePlaylistDialogVisible by remember { mutableStateOf(false) }

    //ListSongs state
    var isListLayout by remember { mutableStateOf(true) }
    var iconDisplayMode = if (isListLayout) R.drawable.ic_list_mode else R.drawable.ic_grid_mode
    var songMenuIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        if (selectedPlaylistIndex == -1 ) { // Playlist mode
            PlaylistLayout(
                isInsertPlaylistDialogVisible = isInsertPlaylistDialogVisible,
                playlists = state.playlists,
                playlistMenuIndex = playlistMenuIndex,
                isRenamePlaylistDialogVisible = isRenamePlaylistDialogVisible,
                headerPlaylistModifier = Modifier,
                mainPlaylistModifier = Modifier,
                onChangeInsertPlaylistDialogState = { isInsertPlaylistDialogVisible = it },
                onSelectPlaylistIndex = { selectedPlaylistIndex = it },
                onSelectPlaylistMenuIndex = { playlistMenuIndex = it },
                onDeletePlaylist = { viewModel.processIntent(PlaylistIntent.DeletePlaylist(it)) },
                onChangeRenamePlaylistDialogState = { isRenamePlaylistDialogVisible = it },
                onChangeRenamingPlaylistIndex = { renamingPlaylistIndex = it },
                onInsertPlaylist = {
                    viewModel.processIntent(
                        PlaylistIntent.InsertPlaylist(playlistName = it)
                    )
                },
                onRenamePlaylist = {
                    viewModel.processIntent(
                        PlaylistIntent.RenamePlaylist(
                            playlistId = state.playlists[renamingPlaylistIndex].playlistId,
                            playlistName = it
                        )
                    )
                }
            )
        } else { // When choose playlist
            SongLayout(
                playlistName = state.playlists[selectedPlaylistIndex].playlistName,
                iconDisplayMode = iconDisplayMode,
                isListLayout = isListLayout,
                songs = transformSongWithPlaylistIdToSong(state.playlists[selectedPlaylistIndex].playlistId, state.songs),
                songMenuIndex = songMenuIndex,
                headerModifier = Modifier,
                mainModifier = Modifier,
                onChangeDisplayMode = { isListLayout = !isListLayout },
                onChangeSelectedPlaylistIndex = { selectedPlaylistIndex = it },
                onChangeSongMenuIndex = { songMenuIndex = it },
                onDeleteSongFromPlaylist = { songId ->
                    viewModel.processIntent(
                        PlaylistIntent.DeleteSongFromPlaylist(
                            playlistId = state.playlists[selectedPlaylistIndex].playlistId,
                            songId = songId
                        )
                    )
                }
            )
        }
    }
}


