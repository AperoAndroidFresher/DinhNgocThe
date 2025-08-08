package com.example.dinhngocthe.presentation.library

sealed interface LibraryEvent {
    data object NavigateToPlaylist : LibraryEvent
    data class PlayMusic(val songId: Long) : LibraryEvent
}