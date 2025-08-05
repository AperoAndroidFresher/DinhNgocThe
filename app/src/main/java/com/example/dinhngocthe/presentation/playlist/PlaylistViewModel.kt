package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.PlaylistRepository
import com.example.dinhngocthe.data.room.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.presentation.login.CurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(context: Application) : ViewModel() {
    private val playlistRepository = PlaylistRepository(context)
    private val _state = MutableStateFlow(PlaylistState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            playlistRepository.getAllPlaylistsByUserId(CurrentUser.id).collect { playlists ->
                _state.update { it.copy(playlists = playlists) }
            }
        }

        viewModelScope.launch {
            playlistRepository.getSongWithPlaylistId().collect { songs ->
                _state.update { it.copy(songs = songs) }
            }
        }
    }

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.AddPlaylist -> viewModelScope.launch(Dispatchers.IO) {
                playlistRepository.insertPlaylist(intent.playlist)
            }

            is PlaylistIntent.RemovePlaylist -> viewModelScope.launch(Dispatchers.IO) {
                playlistRepository.deletePlaylist(intent.playlist)
            }

            is PlaylistIntent.RenamePlaylist -> viewModelScope.launch(Dispatchers.IO) {
                playlistRepository.renamePlaylist(intent.id, intent.name)
            }

            is PlaylistIntent.RemoveSong -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val playlistSongCrossRef = PlaylistSongCrossRef(intent.playlistId, intent.songId)
                    playlistRepository.deleteSongFromPlaylist(playlistSongCrossRef)
                    playlistRepository.updatePlaylistSongCount(intent.playlistId)
                }
            }

            is PlaylistIntent.LoadSongs -> TODO()
        }
    }

    class PlaylistViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlaylistViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct PlaylistViewModelFactory")
        }
    }

}
