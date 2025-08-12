package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.SongWithPlaylistSongCrossRefs

data class PlaylistState(
    val playlists: List<Playlist> = listOf<Playlist>(),
    val songs: List<SongWithPlaylistSongCrossRefs> = listOf<SongWithPlaylistSongCrossRefs>(),
    val currentSongId: Long = -1,
    val currentPlaySourceName: String = ""
)