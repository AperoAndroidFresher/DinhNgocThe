package com.example.dinhngocthe.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dinhngocthe.data.room.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.room.entities.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    // When loading all songs from local storage, only insert the ones that don't already exist in Room
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSongs(songs: List<Song>)

    @Query("SELECT * FROM song")
    fun getAllSongs(): Flow<List<Song>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongToPlaylist(playlistSongCrossRef: PlaylistSongCrossRef)
}