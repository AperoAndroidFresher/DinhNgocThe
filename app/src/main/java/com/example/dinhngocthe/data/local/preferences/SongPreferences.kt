package com.example.dinhngocthe.data.local.preferences

import android.content.Context
import androidx.core.content.edit

class SongPreferences(context: Context) {
    companion object {
        private const val PREF_NAME = "song_prefs"
        private const val KEY_SONG_ID = "current_song_id"
        private const val KEY_CURRENT_PLAY_SOURCE_NAME = "current_play_source_name"
    }

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setSongId(songId: Long) {
        prefs.edit { putLong(KEY_SONG_ID, songId) }
    }

    fun getSongId(): Long {
        return prefs.getLong(KEY_SONG_ID, -1L)
    }

    fun removeSongId() {
        prefs.edit { remove(KEY_SONG_ID) }
    }

    fun setCurrentPlaySourceName(sourceName: String) {
        prefs.edit { putString(KEY_CURRENT_PLAY_SOURCE_NAME, sourceName) }
    }

    fun getCurrentPlaySourceName(): String {
        return prefs.getString(KEY_CURRENT_PLAY_SOURCE_NAME, "") ?: ""
    }

    fun removeCurrentPlaySourceName() {
        prefs.edit { remove(KEY_CURRENT_PLAY_SOURCE_NAME) }
    }
}