package com.example.dinhngocthe.data.local.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "playlist",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Playlist(
    @PrimaryKey(autoGenerate = true) val playlistId: Long = 0,
    val playlistName: String = "",
    val numberOfSongs: Int = 0,
    val coverArtUri: Uri? = null,
    val userId: Long
)