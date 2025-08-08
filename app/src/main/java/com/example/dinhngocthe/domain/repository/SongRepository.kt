package com.example.dinhngocthe.domain.repository

import android.content.Context
import android.hardware.camera2.CaptureFailure
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.remote.model.SongDto
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    suspend fun loadLocalSongsFromDevice(): List<Song>?
    fun getSongsFromRoom(): Flow<List<Song>>
    suspend fun insertAllSongsToRoom(songs: List<Song>)
    suspend fun insertSongToPlaylistInRoom(playlistSongCrossRef: PlaylistSongCrossRef)
    fun getSongsFromRemote(
        onSuccess: (List<SongDto>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
    suspend fun downloadAndSaveSongDtosToStorage(songDtos: List<SongDto>): List<Song>
}