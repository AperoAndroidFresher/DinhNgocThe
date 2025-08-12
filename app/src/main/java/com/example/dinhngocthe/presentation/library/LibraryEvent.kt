package com.example.dinhngocthe.presentation.library

import com.example.dinhngocthe.data.local.entities.Song

sealed interface LibraryEvent {
    data object NavigateToPlaylist : LibraryEvent
    data class PlayMusic(val song: Song, val currentSongSourceName: String) : LibraryEvent
}