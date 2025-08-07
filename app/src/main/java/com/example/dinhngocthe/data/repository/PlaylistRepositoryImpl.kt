package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.local.LocalDatabase
import com.example.dinhngocthe.data.local.dao.PlaylistDao
import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.SongWithPlaylistSongCrossRefs
import com.example.dinhngocthe.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(context: Application) : PlaylistRepository{
    private val localDatabase: LocalDatabase = LocalDatabase.getInstance(context)
    private val playlistDao: PlaylistDao = localDatabase.playlistDao()

    override fun getAllPlaylistsByUserId(userId: Long): Flow<List<Playlist>> {
        return playlistDao.getPlaylistByUserId(userId)
    }

    override suspend fun insertPlaylist(playlist: Playlist) {
        playlistDao.insertPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist)
    }

    override suspend fun renamePlaylist(playlistId: Long, playlistName: String) {
        playlistDao.renamePlaylist(playlistId, playlistName)
    }

    override suspend fun updatePlaylistSongCount(playlistId: Long) {
        playlistDao.updatePlaylistSongCount(playlistId)
    }

    override fun getSongWithPlaylistId(): Flow<List<SongWithPlaylistSongCrossRefs>> {
        return playlistDao.getSongWithPlaylistSongCrossRefs()
    }

    override suspend fun deleteSongFromPlaylist(playlistSongCrossRef: PlaylistSongCrossRef) {
        playlistDao.deleteSongFromPlaylist(playlistSongCrossRef)
    }
}