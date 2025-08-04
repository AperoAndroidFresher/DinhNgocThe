package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.room.LocalDatabase
import com.example.dinhngocthe.data.room.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.room.entities.Song
import kotlinx.coroutines.flow.Flow

class SongRepository(context: Application) {
    private val localDatabase = LocalDatabase.getInstance(context)
    private val songDao = localDatabase.songDao()

    suspend fun insertAllSongs(songs: List<Song>) {
        songDao.insertAllSongs(songs)
    }

    fun getAllSongs() : Flow<List<Song>> = songDao.getAllSongs()

    suspend fun addSongToPlaylist(crossRef: PlaylistSongCrossRef) {
        songDao.addSongToPlaylist(crossRef)
    }
}