package com.example.dinhngocthe.presentation.library

import android.content.Intent

sealed interface LibraryIntent {
    data object ToggleLocalButton : LibraryIntent
    data object ToggleRemoteButton : LibraryIntent
    data class ShowMenu(val index: Int) : LibraryIntent
    data object DismissMenu : LibraryIntent
    data object AddToPlaylist : LibraryIntent
}