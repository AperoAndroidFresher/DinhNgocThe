package com.example.dinhngocthe.utils

import android.annotation.SuppressLint
import com.example.dinhngocthe.data.room.entities.Song
import com.example.dinhngocthe.data.room.entities.SongWithPlaylistSongCrossRefs

fun transformSongWithPlaylistIdToSong(
    playlistId: Long,
    songs: List<SongWithPlaylistSongCrossRefs>
): List<Song> = songs.filter { it.playlistSongCrossRefs.any{ it.playlistId == playlistId } }.map { it.song }

@SuppressLint("DefaultLocale")
fun formatDuration(durationInMillis: Long): String {
    val totalSeconds = durationInMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}