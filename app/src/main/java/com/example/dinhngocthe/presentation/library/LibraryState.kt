package com.example.dinhngocthe.presentation.library

import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.Song


data class LibraryState(
    val localSongs: List<Song> = listOf<Song>(),
    val remoteSongs: List<Song> = listOf<Song>(),
    val playlists: List<Playlist> = listOf<Playlist>(),
    val isLoading: Boolean = false,
    val error: String = ""
)