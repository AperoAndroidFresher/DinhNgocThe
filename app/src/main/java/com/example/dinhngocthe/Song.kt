package com.example.dinhngocthe
import com.example.dinhngocthe.R
data class Song(
    val name: String = "Song name",
    val singers: String = "Singer",
    val duration: Long = 240_000L,
    val coverArt: Int = R.drawable.img_song_default
)