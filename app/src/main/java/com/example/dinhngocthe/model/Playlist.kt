package com.example.dinhngocthe.model

import android.net.Uri

data class Playlist(
    val id: Long = 0L,
    val name: String = "",
    val numberSong: Int = 0,
    val coverArt: Uri? = null,
    val listSongs: List<Song> = listOf<Song>()
)