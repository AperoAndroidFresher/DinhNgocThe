package com.example.dinhngocthe.presentation.library

sealed interface LibraryIntent {
    data class AddMusicToPlaylist(val playlistId: Long, val songId: Long) : LibraryIntent
    data object NavigateToPlaylist : LibraryIntent
    data object LoadData : LibraryIntent
}