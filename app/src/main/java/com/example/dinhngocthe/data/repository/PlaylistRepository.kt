package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.room.LocalDatabase
import com.example.dinhngocthe.data.room.dao.PlaylistDao
import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.room.entities.SongWithPlaylistSongCrossRefs
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(context: Application) {
    private val localDatabase: LocalDatabase = LocalDatabase.getInstance(context)
    private val playlistDao: PlaylistDao = localDatabase.playlistDao()

    fun getAllPlaylistsByUserId(userId: Long): Flow<List<Playlist>> {
        return playlistDao.getPlaylistByUserId(userId)
    }

    suspend fun insertPlaylist(playlist: Playlist) {
        playlistDao.insertPlaylist(playlist)
    }

    suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist)
    }

    suspend fun renamePlaylist(playlistId: Long, playlistName: String) {
        playlistDao.renamePlaylist(playlistId, playlistName)
    }

    suspend fun updatePlaylistSongCount(playlistId: Long) {
        playlistDao.updatePlaylistSongCount(playlistId)
    }

    fun getSongWithPlaylistId(): Flow<List<SongWithPlaylistSongCrossRefs>> {
        return playlistDao.getSongWithPlaylistSongCrossRefs()
    }

    suspend fun deleteSongFromPlaylist(playlistSongCrossRef: PlaylistSongCrossRef) {
        playlistDao.deleteSongFromPlaylist(playlistSongCrossRef)
    }
}