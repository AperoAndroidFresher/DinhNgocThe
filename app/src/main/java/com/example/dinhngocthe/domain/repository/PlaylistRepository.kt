package com.example.dinhngocthe.domain.repository

import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.SongWithPlaylistSongCrossRefs
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylistsByUserId(userId: Long): Flow<List<Playlist>>

    suspend fun insertPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    suspend fun renamePlaylist(playlistId: Long, playlistName: String)

    suspend fun updatePlaylistSongCount(playlistId: Long)

    fun getSongWithPlaylistId(): Flow<List<SongWithPlaylistSongCrossRefs>>

    suspend fun deleteSongFromPlaylist(playlistSongCrossRef: PlaylistSongCrossRef)
}