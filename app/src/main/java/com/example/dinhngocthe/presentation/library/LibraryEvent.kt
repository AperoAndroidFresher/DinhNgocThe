package com.example.dinhngocthe.presentation.library

sealed interface LibraryEvent {
    data object NavigateToPlaylist : LibraryEvent
    data class PlayMusic(val currentSongId: Long, val songIds: List<Long>, val currentPlaySourceName: String) : LibraryEvent
}