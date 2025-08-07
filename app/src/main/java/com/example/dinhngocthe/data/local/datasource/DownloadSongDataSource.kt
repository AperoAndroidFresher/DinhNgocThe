package com.example.dinhngocthe.data.local.datasource

import android.content.Context
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.remote.model.SongDto
import java.io.File

interface DownloadSongDataSource {
    suspend fun downloadAndSaveSongDtosToStorage(context: Context, songDtoS: List<SongDto>): List<Song>
}