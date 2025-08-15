package com.example.dinhngocthe.service.musicstate

import android.net.Uri

data class MusicState(
    val songId: Long = 0,
    val singer: String = "",
    val songName: String = "",
    val coverArtUri: Uri? = null,
    val progress: Float = 0f,
    val duration: Long = 0L,
    val currentPlaySourceName: String = "",

    val isPlaying: Boolean = false,
    val isActive: Boolean = false,
    val isShuffle: Boolean = false,
    val isRepeat: Boolean = false,
    val enableShuffle: Boolean = true,
    val enableRepeat: Boolean = true,
    val enablePrevious: Boolean = true,
    val enableNext: Boolean = true
)