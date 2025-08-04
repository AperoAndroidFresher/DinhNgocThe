package com.example.dinhngocthe.presentation.library

import android.content.Intent
import com.example.dinhngocthe.data.room.entities.Song

sealed interface LibraryIntent {
    data class AddToPlaylist(val playlistId: Long, val songId: Long) : LibraryIntent
    data object NavigateToPlaylist : LibraryIntent
}