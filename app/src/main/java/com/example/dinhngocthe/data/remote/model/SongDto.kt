package com.example.dinhngocthe.data.remote.model

import android.net.Uri
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.local.entities.SongSource
import com.google.gson.annotations.SerializedName

data class SongDto(
    @SerializedName("title")
    val songName: String = "",
    @SerializedName("artist")
    val singer: String = "",
    val kind: String = "",
    val duration: String = "",
    val path: String = "",
)

fun SongDto.toSong(audioUri: Uri? = null, coverArtUri: Uri? = null): Song {
    return Song(
        songName = this.songName,
        singer = this.singer,
        duration = this.duration.toLongOrNull() ?: 0L,
        coverArtUri = coverArtUri,
        source = SongSource.REMOTE,
        audioUri = audioUri
    )
}