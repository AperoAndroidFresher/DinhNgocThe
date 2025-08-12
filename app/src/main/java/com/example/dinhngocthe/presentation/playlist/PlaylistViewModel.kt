package com.example.dinhngocthe.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.datastore.UserDataStore
import com.example.dinhngocthe.domain.repository.PlaylistRepository
import com.example.dinhngocthe.presentation.library.MusicPlayerLibrary
import com.example.dinhngocthe.service.musicstate.MusicStateHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val userPrefs: UserDataStore,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    private val _state: MutableStateFlow<PlaylistState> = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<PlaylistEvent> = MutableSharedFlow<PlaylistEvent>()
    val event: SharedFlow<PlaylistEvent> = _event.asSharedFlow()

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.LoadData -> {
                handleLoadData()
            }

            is PlaylistIntent.InsertPlaylist -> {
                handleInsertPlaylist(intent)
            }

            is PlaylistIntent.DeletePlaylist -> {
                handleDeletePlaylist(intent)
            }

            is PlaylistIntent.RenamePlaylist -> {
                handleRenamePlaylist(intent)
            }

            is PlaylistIntent.DeleteSongFromPlaylist -> {
                handleDeleteSongFromPlaylist(intent)
            }

            is PlaylistIntent.PlayMusic -> {
                handlePlayMusic(intent)
            }
        }
    }

    private fun handlePlayMusic(intent: PlaylistIntent.PlayMusic) {
        viewModelScope.launch {
            _event.emit(PlaylistEvent.PlayMusic(
                currentSongId = intent.songId,
                currentPlaySourceName = intent.currentPlaySourceName,
                songIds = intent.songIds
            ))
        }
    }

    private fun handleLoadData() {
        viewModelScope.launch {
            playlistRepository.getAllPlaylistsByUserId(userPrefs.getUserId()!!).collect { playlists ->
                _state.update { it.copy(playlists = playlists) }
            }
        }

        viewModelScope.launch {
            playlistRepository.getSongWithPlaylistId().collect { songs ->
                _state.update { it.copy(songs = songs) }
            }
        }

        viewModelScope.launch {
            MusicStateHolder.state.collectLatest { musicState ->
                _state.update {
                    it.copy(
                        currentSongId = musicState.songId,
                        currentPlaySourceName = musicState.currentPlaySourceName
                    )
                }
            }
        }
    }

    private fun handleInsertPlaylist(intent: PlaylistIntent.InsertPlaylist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistRepository.insertPlaylist(
                Playlist(
                    playlistName = intent.playlistName,
                    userId = userPrefs.getUserId()!!
                )
            )
        }
    }

    private fun handleDeletePlaylist(intent: PlaylistIntent.DeletePlaylist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistRepository.deletePlaylist(intent.playlist)
        }
    }

    private fun handleRenamePlaylist(intent: PlaylistIntent.RenamePlaylist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistRepository.renamePlaylist(intent.playlistId, intent.playlistName)
        }
    }

    private fun handleDeleteSongFromPlaylist(intent: PlaylistIntent.DeleteSongFromPlaylist) {
        viewModelScope.launch(Dispatchers.IO) {
            val playlistSongCrossRef = PlaylistSongCrossRef(intent.playlistId, intent.songId)
            playlistRepository.deleteSongFromPlaylist(playlistSongCrossRef)
            playlistRepository.updatePlaylistSongCount(intent.playlistId)
        }
    }
}
