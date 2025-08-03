package com.example.dinhngocthe.presentation.library

sealed interface LibraryEvent {
    data object NavigateToPlaylist : LibraryEvent
}