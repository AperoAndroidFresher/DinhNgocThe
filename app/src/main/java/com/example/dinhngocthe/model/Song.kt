package com.example.dinhngocthe.model

import android.net.Uri
import com.example.dinhngocthe.R

data class Song(
    val id: Int = 0,
    val name: String = "Song name",
    val singers: String = "Singer",
    val duration: Long = 240_000L,
    val coverArt: Uri? = null
)