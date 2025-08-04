package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.Song

data class PlaylistState(
    val playlists: List<Playlist> = listOf<Playlist>(),
    val songs: List<Song> = listOf<Song>()
)