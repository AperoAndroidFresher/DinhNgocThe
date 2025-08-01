package com.example.dinhngocthe.presentation.library

import com.example.dinhngocthe.model.Song

data class LibraryState(
    val displayMode: String = "local",
    val localSongs: List<Song> = listOf<Song>(),
    val remoteSongs: List<Song> = listOf<Song>(),
    val menuExpandedIndex: Int = -1,
    val isLoading: Boolean = false,
    val error: String = ""
)