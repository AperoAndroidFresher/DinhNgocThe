package com.example.dinhngocthe.presentation.musicplayer

sealed interface MusicPlayerIntent {
    data object LoadData : MusicPlayerIntent
    data object CloseMusic : MusicPlayerIntent
    data object PlayPauseMusic : MusicPlayerIntent
    data object NextMusic : MusicPlayerIntent
    data object PreviousMusic : MusicPlayerIntent
}