package com.example.dinhngocthe.presentation.miniplayer

sealed interface MiniPlayerEvent {
    data object PlayPauseMusic : MiniPlayerEvent
    data object CloseMusic : MiniPlayerEvent
}