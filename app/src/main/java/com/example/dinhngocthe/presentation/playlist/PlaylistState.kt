package com.example.dinhngocthe.presentation.playlist

import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.SongWithPlaylistSongCrossRefs

data class PlaylistState(
    val playlists: List<Playlist> = listOf<Playlist>(),
    val songs: List<SongWithPlaylistSongCrossRefs> = listOf<SongWithPlaylistSongCrossRefs>()
)