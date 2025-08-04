package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.data.room.entities.Playlist


sealed interface PlaylistIntent {
    data class AddPlaylist(val playlist: Playlist) : PlaylistIntent
    data class RemovePlaylist(val playlist: Playlist) : PlaylistIntent
    data class RenamePlaylist(val id: Long, val name: String) : PlaylistIntent
    data class RemoveSong(val playlistIndex: Int, val songIndex: Int) : PlaylistIntent
    data class loadSongs(val playlistId: Long) : PlaylistIntent
}
