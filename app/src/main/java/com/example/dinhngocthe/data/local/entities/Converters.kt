package com.example.dinhngocthe.data.local.entities

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromUri(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    fun toUri(uriString: String?): Uri? = uriString?.let { Uri.parse(it) }

    @TypeConverter
    fun fromSongSource(source: SongSource): String = source.name

    @TypeConverter
    fun toSongSource(source: String): SongSource = SongSource.valueOf(source)
}
