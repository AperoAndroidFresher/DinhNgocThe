package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.PlaylistRepository
import com.example.dinhngocthe.model.Playlists
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
        viewModelScope.launch(Dispatchers.IO) {
            playlistRepository.getAllPlaylists(CurrentUser.id).collect { playlists ->
                _state.update { it.copy(playlists = playlists) }
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
                val playlists = Playlists.playlists.value.toMutableList()
                val playlist = playlists[intent.playlistIndex]
                val updatedSongs = playlist.listSongs.toMutableList().apply {
                    removeAt(intent.songIndex)
                }
                playlists[intent.playlistIndex] = playlist.copy(
                    listSongs = updatedSongs,
                    numberSong = updatedSongs.size
                )
                Playlists.playlists.value = playlists
            }

            is PlaylistIntent.loadSongs -> TODO()
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
