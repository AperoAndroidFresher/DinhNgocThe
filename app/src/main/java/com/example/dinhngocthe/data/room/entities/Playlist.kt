package com.example.dinhngocthe.data.room.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "playlist",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Playlist(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val numberSong: Int = 0,
    val coverArt: Uri? = null,
    val userId: Long
)