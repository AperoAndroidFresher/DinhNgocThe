package com.example.dinhngocthe.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class UserDataStore(private val context: Context) {
    companion object {
        private const val DATASTORE_NAME = "user_prefs"
        private val KEY_USER_ID = longPreferencesKey("current_user_id")
        private val KEY_REMEMBER_ME = booleanPreferencesKey("remember_me")
        private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)
    }

    suspend fun setUserId(userId: Long) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = userId
        }
    }

    suspend fun getUserId(): Long? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_USER_ID]
    }

    suspend fun removeUserId() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_USER_ID)
        }
    }

    suspend fun setRememberMe(remember: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_REMEMBER_ME] = remember
        }
    }

    suspend fun isRememberMe(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_REMEMBER_ME] ?: false
    }
}
