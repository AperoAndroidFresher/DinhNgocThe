package com.example.dinhngocthe.presentation.library

import android.content.Intent
import com.example.dinhngocthe.model.Song

sealed interface LibraryIntent {
    data class AddToPlaylist(val playlistIndex: Int, val song: Song) : LibraryIntent
    data object NavigateToPlaylist : LibraryIntent
}