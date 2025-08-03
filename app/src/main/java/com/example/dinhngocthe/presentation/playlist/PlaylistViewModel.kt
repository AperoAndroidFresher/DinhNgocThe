package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.model.Playlists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(private val context: Application) : ViewModel() {
    private val _state = MutableStateFlow(PlaylistState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            Playlists.playlists.collect { newList ->
                _state.update { it.copy(playlists = newList) }
            }
        }
    }

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.AddPlaylist -> {
                Playlists.playlists.value = Playlists.playlists.value + intent.playlist
            }

            is PlaylistIntent.RemovePlaylist -> {
                Playlists.playlists.value = Playlists.playlists.value - Playlists.playlists.value[intent.index]
            }

            is PlaylistIntent.RenamePlaylist -> {
                val playlists = Playlists.playlists.value.toMutableList()
                val playlist = playlists[intent.index]
                playlists[intent.index] = playlist.copy(name = intent.name)
                Playlists.playlists.value = playlists
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
