package com.example.dinhngocthe.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(context: Context) {
    companion object {
        private const val PREF_NAME = "user_prefs"
        private const val KEY_USER_ID = "current_user_id"
        private const val KEY_REMEMBER_ME = "remember_me"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUserId(userId: Long) {
        prefs.edit { putLong(KEY_USER_ID, userId) }
    }

    fun getUserId(): Long? {
        val id = prefs.getLong(KEY_USER_ID, -1L)
        return if (id == -1L) null else id
    }

    fun removeUserId() {
        prefs.edit { remove(KEY_USER_ID) }
    }

    fun setRememberMe(remember: Boolean) {
        prefs.edit { putBoolean(KEY_REMEMBER_ME, remember) }
    }

    fun isRememberMe(): Boolean {
        return prefs.getBoolean(KEY_REMEMBER_ME, false)
    }
}
