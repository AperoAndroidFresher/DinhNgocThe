package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.model.Song

data class PlaylistState(
    val songs: List<Song> = emptyList(),
    val isListMode: Boolean = true,
    val expandedMenuIndex: Int = -1
)
