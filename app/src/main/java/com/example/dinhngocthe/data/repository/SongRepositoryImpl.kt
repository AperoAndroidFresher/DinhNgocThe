package com.example.dinhngocthe.data.repository

import android.app.Application
import com.example.dinhngocthe.data.local.LocalDatabase
import com.example.dinhngocthe.data.local.dao.SongDao
import com.example.dinhngocthe.data.local.datasource.loadAllSongs
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.remote.api.ApiClient
import com.example.dinhngocthe.data.remote.model.SongDto
import com.example.dinhngocthe.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongRepositoryImpl(private val context: Application) : SongRepository {
    private val localDatabase: LocalDatabase = LocalDatabase.getInstance(context)
    private val songDao: SongDao = localDatabase.songDao()

    override fun loadLocalSongsFromDevice(): List<Song>? {
        return loadAllSongs(context)
    }

    override fun getSongsFromRoom(): Flow<List<Song>> {
        return songDao.getAllSongs()
    }

    override suspend fun insertAllSongsToRoom(songs: List<Song>) {
        songDao.insertAllSongs(songs)
    }

    override suspend fun insertSongToPlaylistInRoom(playlistSongCrossRef: PlaylistSongCrossRef) {
        songDao.insertSongToPlaylist(playlistSongCrossRef)
    }

    override fun getSongsFromRemote(
        onSuccess: (List<SongDto>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = ApiClient.build().getSongs()
        call.enqueue(object : Callback<List<SongDto>> {
            override fun onResponse(call: Call<List<SongDto>>, response: Response<List<SongDto>>) {
                when {
                    response.isSuccessful -> {
                        val songs = response.body()
                        songs?.let { onSuccess(it) }
                    }

                    response.code() == 400 -> {
                        onFailure(Exception("Bad request"))
                    }

                    response.code() == 401 -> {
                        onFailure(Exception("Unauthorized"))
                    }

                    response.code() == 403 -> {
                        onFailure(Exception("Forbidden"))
                    }

                    response.code() == 404 -> {
                        onFailure(Exception("Not Found"))
                    }

                    response.code() == 500 -> {
                        onFailure(Exception("Internal Server Error"))
                    }

                    else -> {
                        onFailure(Exception("Unknown Error"))
                    }
                }
            }

            override fun onFailure(call: Call<List<SongDto>?>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}