package com.example.dinhngocthe.service.musicstate

import android.net.Uri

data class MusicState(
    val songId: Long = 0,
    val currentPlaySourceName: String = "",
    val singer: String = "",
    val coverArtUri: Uri? = null,
    val progress: Float = 0f,
    val songName: String = "",
    val duration: Long = 0L,
    val isPlaying: Boolean = false,
    val isActive: Boolean = false,
    val isShuffle: Boolean = false,
    val isRepeat: Boolean = false,
)