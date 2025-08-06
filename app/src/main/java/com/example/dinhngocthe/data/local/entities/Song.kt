package com.example.dinhngocthe.data.local.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "song",
    indices = [Index(value = ["songName", "singer", "duration", "source"], unique = true)]
)
data class Song(
    @PrimaryKey(autoGenerate = true) val songId: Long = 0,
    val songName: String = "",
    val singer: String = "",
    val duration: Long = 0L,
    val coverArtUri: Uri? = null,
    val source: SongSource = SongSource.LOCAL
)