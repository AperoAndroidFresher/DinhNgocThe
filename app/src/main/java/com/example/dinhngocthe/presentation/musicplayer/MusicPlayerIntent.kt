package com.example.dinhngocthe.presentation.musicplayer

sealed interface MusicPlayerIntent {
    data object LoadData : MusicPlayerIntent
    data object CloseMusic : MusicPlayerIntent
    data object PlayPauseMusic : MusicPlayerIntent
    data object NextMusic : MusicPlayerIntent
    data object PreviousMusic : MusicPlayerIntent
    data class UpdateProgress(val progress: Float) : MusicPlayerIntent
    data class OnChangeProgress(val progress: Float) : MusicPlayerIntent
    data object Shuffle : MusicPlayerIntent
    data object Repeat : MusicPlayerIntent
}