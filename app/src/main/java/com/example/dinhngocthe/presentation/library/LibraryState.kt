package com.example.dinhngocthe.presentation.library

import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.Song


data class LibraryState(
    val localSongs: List<Song> = listOf<Song>(),
    val remoteSongs: List<Song> = listOf<Song>(),
    val playlists: List<Playlist> = listOf<Playlist>(), // When selecting playlist to add
    val currentSongId: Long = -1L,
    val currentPlaySourceName: String = "",

    val isLoadingLocalSongs: Boolean = false,
    val isLoadingRemoteSongs: Boolean = false,
    val localError: String = "",
    val remoteError: String = ""
)