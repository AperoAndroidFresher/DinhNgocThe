package com.example.dinhngocthe.presentation.musicplayer

sealed interface MusicPlayerIntent {
    data object LoadData : MusicPlayerIntent
}