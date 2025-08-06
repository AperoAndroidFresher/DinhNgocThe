package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.data.local.entities.Playlist


sealed interface PlaylistIntent {
    data class InsertPlaylist(val playlist: Playlist) : PlaylistIntent
    data class DeletePlaylist(val playlist: Playlist) : PlaylistIntent
    data class RenamePlaylist(val playlistId: Long, val playlistName: String) : PlaylistIntent
    data class DeleteSongFromPlaylist(val playlistId: Long, val songId: Long) : PlaylistIntent
    data object LoadData : PlaylistIntent
}
