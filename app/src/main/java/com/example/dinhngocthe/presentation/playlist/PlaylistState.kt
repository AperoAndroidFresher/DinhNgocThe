package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.model.Playlist

data class PlaylistState(
    val playlists: List<Playlist> = listOf<Playlist>()
)