package com.example.dinhngocthe.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dinhngocthe.data.room.entities.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist WHERE userId = :userId")
    fun getAllPlaylists(userId: Long) : Flow<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Query("""
        UPDATE playlist
        SET name = :name
        WHERE id = :id
    """)
    suspend fun renamePlaylist(id: Long, name: String)

    @Query("""
        UPDATE playlist
        SET numberSong = numberSong + :number
        WHERE id = :id
    """)
    suspend fun updateNumberSong(number: Int, id: Long)
}