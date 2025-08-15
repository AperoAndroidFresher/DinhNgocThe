package com.example.dinhngocthe.presentation.miniplayer

sealed interface MiniPlayerIntent {
    data object LoadData : MiniPlayerIntent
    data object PlayPauseMusic : MiniPlayerIntent
    data object CloseMusic : MiniPlayerIntent
}