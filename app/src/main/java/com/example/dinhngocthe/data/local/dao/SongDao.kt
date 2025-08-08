package com.example.dinhngocthe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    // When loading all songs from local storage, only insert the ones that don't already exist in Room
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSongs(songs: List<Song>)

    @Query("SELECT * FROM song ORDER BY songName ASC")
    fun getAllSongs(): Flow<List<Song>>

    @Query("SELECT * FROM song ORDER BY songName ASC")
    suspend fun getAllSongsService(): List<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongToPlaylist(playlistSongCrossRef: PlaylistSongCrossRef)
}