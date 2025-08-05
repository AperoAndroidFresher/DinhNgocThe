package com.example.dinhngocthe.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["playlistId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["playlistId"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Song::class,
            parentColumns = ["songId"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistSongCrossRef(
    val playlistId: Long,
    val songId: Long
)
