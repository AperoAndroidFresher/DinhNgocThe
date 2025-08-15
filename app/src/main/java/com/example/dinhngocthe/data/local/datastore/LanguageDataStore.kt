package com.example.dinhngocthe.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LanguageDataStore(private val context: Context) {
    companion object {
        private const val DATASTORE_NAME = "language_prefs"
        private val KEY_LANGUAGE_CODE = stringPreferencesKey("language_code")
        private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)
    }

    suspend fun setLanguageCode(languageCode: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LANGUAGE_CODE] = languageCode
        }
    }

    suspend fun getLanguageCode(): String {
        return context.dataStore.data.first()[KEY_LANGUAGE_CODE] ?: "en"
    }

    val languageFlow: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[KEY_LANGUAGE_CODE] ?: "en" }
}