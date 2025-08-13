package com.example.dinhngocthe.presentation.musicplayer

sealed interface MusicPlayerEvent {
    data object CloseMusic : MusicPlayerEvent
    data object PlayPauseMusic : MusicPlayerEvent
    data object NextMusic : MusicPlayerEvent
    data object PreviousMusic : MusicPlayerEvent
    data class UpdateProgress(val progress: Float) : MusicPlayerEvent
    data object StopUpdateProgress : MusicPlayerEvent
    data object Shuffle : MusicPlayerEvent
    data object Repeat : MusicPlayerEvent
}