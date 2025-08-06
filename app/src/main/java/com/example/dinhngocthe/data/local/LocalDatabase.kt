package com.example.dinhngocthe.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dinhngocthe.data.local.dao.PlaylistDao
import com.example.dinhngocthe.data.local.dao.SongDao
import com.example.dinhngocthe.data.local.dao.UserDao
import com.example.dinhngocthe.data.local.entities.Converters
import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.local.entities.User
@Database(entities = [User::class, Song::class, Playlist::class, PlaylistSongCrossRef::class], version = 2)

@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Application) : LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, LocalDatabase::class.java, "dinhngocthe_database")
                    . fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}