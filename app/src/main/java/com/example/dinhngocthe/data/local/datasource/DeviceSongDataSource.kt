package com.example.dinhngocthe.data.local.datasource

import android.app.Application
import com.example.dinhngocthe.data.local.entities.Song

interface DeviceSongDataSource {
    suspend fun loadAllSongsFromDevice(context: Application): List<Song>?
}