package com.example.dinhngocthe.presentation.library

sealed interface LibraryIntent {
    data class AddMusicToPlaylist(val playlistId: Long, val songId: Long) : LibraryIntent
    data class PlayMusic(val currentSongId: Long, val songIds: List<Long>, val currentPlaySourceName: String) : LibraryIntent
    data object NavigateToPlaylist : LibraryIntent
    data object LoadData : LibraryIntent
    data object ViewOffline : LibraryIntent
}