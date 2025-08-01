package com.example.dinhngocthe.presentation.library

sealed interface LibraryEvent {
    data class NavigateToChoosePlaylist(val id: Int) : LibraryEvent
}