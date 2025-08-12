package com.example.dinhngocthe.presentation.library

import com.example.dinhngocthe.data.local.entities.Song

sealed interface LibraryIntent {
    data class AddMusicToPlaylist(val playlistId: Long, val songId: Long) : LibraryIntent
    data class PlayMusic(val song: Song, val currentPlaySourceName: String) : LibraryIntent
    data object NavigateToPlaylist : LibraryIntent
    data object LoadData : LibraryIntent
    data object ViewOffline : LibraryIntent
}