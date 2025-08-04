package com.example.dinhngocthe.data.room.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val singer: String = "",
    val duration: Long = 0L,
    val coverArt: Uri? = null
)