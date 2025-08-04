package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.room.LocalDatabase
import com.example.dinhngocthe.data.room.entities.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(context: Application) {
    private val localDatabase = LocalDatabase.getInstance(context)
    private val playlistDao = localDatabase.playlistDao()

    fun getAllPlaylists(userId: Long) : Flow<List<Playlist>> = playlistDao.getAllPlaylists(userId)

    suspend fun insertPlaylist(playlist: Playlist) {
        playlistDao.insertPlaylist(playlist)
    }

    suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist)
    }

    suspend fun renamePlaylist(id: Long, name: String) {
        playlistDao.renamePlaylist(id, name)
    }

    suspend fun updateNumberSong(number: Int, id: Long) {
        playlistDao.updateNumberSong(number, id)
    }
}