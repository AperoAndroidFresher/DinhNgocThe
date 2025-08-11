package com.example.dinhngocthe.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MusicDataStore(private val context: Context) {
    companion object {
        private const val DATASTORE_NAME = "music_prefs"
        private val KEY_CURRENT_SONG_ID = longPreferencesKey("current_song_id")
        private val KEY_CURRENT_PLAY_SOURCE_NAME = stringPreferencesKey("current_play_source_name")
        private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)
    }

    val currentSongIdFlow: Flow<Long> = context.dataStore.data
        .map { prefs -> prefs[KEY_CURRENT_SONG_ID] ?: -1L }

    val currentPlaySourceNameFlow: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[KEY_CURRENT_PLAY_SOURCE_NAME] ?: "" }

    suspend fun setSongId(songId: Long) {
        context.dataStore.edit { prefs ->
            prefs[KEY_CURRENT_SONG_ID] = songId
        }
    }

    suspend fun removeSongId() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_CURRENT_SONG_ID)
        }
    }

    suspend fun setCurrentPlaySourceName(sourceName: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_CURRENT_PLAY_SOURCE_NAME] = sourceName
        }
    }

    suspend fun removeCurrentPlaySourceName() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_CURRENT_PLAY_SOURCE_NAME)
        }
    }
}
