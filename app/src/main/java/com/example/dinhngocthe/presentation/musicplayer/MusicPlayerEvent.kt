package com.example.dinhngocthe.presentation.musicplayer

sealed interface MusicPlayerEvent {
    data object CloseMusic : MusicPlayerEvent
    data object PlayPauseMusic : MusicPlayerEvent
    data object NextMusic : MusicPlayerEvent
    data object PreviousMusic : MusicPlayerEvent
}