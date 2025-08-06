package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.PlaylistRepository
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
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
        }
    }

    private fun handleLoadData() {
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

    private fun handleInsertPlaylist(intent: PlaylistIntent.InsertPlaylist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistRepository.insertPlaylist(intent.playlist)
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
