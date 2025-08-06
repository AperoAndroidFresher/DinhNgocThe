package com.example.dinhngocthe.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.SongWithPlaylistSongCrossRefs
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist WHERE userId = :userId")
    fun getPlaylistByUserId(userId: Long) : Flow<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Query(
        """
        UPDATE playlist
        SET playlistName = :playlistName
        WHERE playlistId = :playlistId
    """
    )
    suspend fun renamePlaylist(playlistId: Long, playlistName: String)

    @Query(
        """
        UPDATE playlist
        SET numberOfSongs = (
            SELECT COUNT(*)
            FROM playlist_song_cross_ref
            WHERE playlistId = :playlistId
        )
        WHERE playlistId = :playlistId
    """
    )
    suspend fun updatePlaylistSongCount(playlistId: Long)

    @Transaction
    @Query("SELECT * FROM song")
    fun getSongWithPlaylistSongCrossRefs() : Flow<List<SongWithPlaylistSongCrossRefs>>

    @Delete
    suspend fun deleteSongFromPlaylist(playlistSongCrossRef: PlaylistSongCrossRef)
}