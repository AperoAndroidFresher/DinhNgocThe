package com.example.dinhngocthe.data.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SongWithPlaylistSongCrossRefs(
    @Embedded val song: Song,
    @Relation(
        parentColumn = "songId",
        entityColumn = "songId",
        entity = PlaylistSongCrossRef::class
    )
    val playlistSongCrossRefs: List<PlaylistSongCrossRef>
)