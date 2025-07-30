package com.example.dinhngocthe.presentation.playlist

sealed interface PlaylistIntent {
    data object ToggleDisplayMode : PlaylistIntent
    data class ShowMenu(val index: Int) : PlaylistIntent
    data object DismissMenu : PlaylistIntent
    data class RemoveSong(val index: Int) : PlaylistIntent
    data class ShareSong(val index: Int) : PlaylistIntent
}
