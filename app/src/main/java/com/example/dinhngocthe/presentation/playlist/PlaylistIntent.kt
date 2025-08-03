package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.model.Playlist

sealed interface PlaylistIntent {
    data class AddPlaylist(val playlist: Playlist) : PlaylistIntent
    data class RemovePlaylist(val index: Int) : PlaylistIntent
    data class RenamePlaylist(val index: Int, val name: String) : PlaylistIntent
    data class RemoveSong(val playlistIndex: Int, val songIndex: Int) : PlaylistIntent
}
