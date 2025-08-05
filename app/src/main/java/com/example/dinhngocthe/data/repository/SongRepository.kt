package com.example.dinhngocthe.data.repository

import android.app.Application
import android.content.ContentUris
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.dinhngocthe.data.room.LocalDatabase
import com.example.dinhngocthe.data.room.dao.SongDao
import com.example.dinhngocthe.data.room.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.room.entities.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import java.io.File

class SongRepository(private val context: Application) {
    private val localDatabase: LocalDatabase = LocalDatabase.getInstance(context)
    private val songDao: SongDao = localDatabase.songDao()

    suspend fun insertAllSongs(songs: List<Song>) {
        songDao.insertAllSongs(songs)
    }

    fun getAllSongs() : Flow<List<Song>> = songDao.getAllSongs()

    suspend fun addSongToPlaylist(playlistSongCrossRef: PlaylistSongCrossRef) {
        songDao.insertSongToPlaylist(playlistSongCrossRef)
    }

    fun loadLocalSongs(): List<Song>? {
        try {
            val contentResolver = context.contentResolver
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION
            )
            val selection = MediaStore.Audio.Media.IS_MUSIC

            val songs = mutableListOf<Song>()
            contentResolver.query(uri, projection, selection, null, null)?.use { it ->
                val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                while (it.moveToNext()) {
                    val id = it.getLong(idCol)
                    val title = it.getString(titleCol) ?: ""
                    val artist = it.getString(artistCol) ?: ""
                    val duration = it.getLong(durationCol)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                    )

                    val embeddedCoverUri: Uri? = try {
                        val mmr = MediaMetadataRetriever()
                        mmr.setDataSource(context, contentUri)
                        mmr.embeddedPicture?.let { bytes ->
                            val file = File(context.cacheDir, "$id")
                            file.writeBytes(bytes)
                            Uri.fromFile(file)
                        }.also {
                            mmr.release()
                        }
                    } catch (_: Exception) {
                        null
                    }

                    songs.add(
                        Song(
                            songId = id,
                            songName = title.dropLast(4),
                            singer = artist,
                            duration = duration,
                            coverArtUri = embeddedCoverUri
                        )
                    )
                }
            }

            return songs
        } catch (e: Exception) {
            Log.e("LibraryViewModel", "Error when loading songs: ${e.message}", e)
            return null
        }
    }
}