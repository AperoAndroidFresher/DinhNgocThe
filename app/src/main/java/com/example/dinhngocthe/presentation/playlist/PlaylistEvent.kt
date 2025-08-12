package com.example.dinhngocthe.presentation.playlist

sealed interface PlaylistEvent {
    data class PlayMusic(
        val currentSongId: Long,
        val currentPlaySourceName: String,
        val songIds: List<Long>
    ) : PlaylistEvent
}