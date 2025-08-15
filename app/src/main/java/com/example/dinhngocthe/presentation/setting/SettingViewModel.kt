package com.example.dinhngocthe.presentation.setting

import android.app.Activity
import android.app.Application
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.datastore.LanguageDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class SettingViewModel(
    private val languageDataStore: LanguageDataStore,
    private val appContext: Application
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state: StateFlow<SettingState> = _state.asStateFlow()

    fun processIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.ChangeLanguage -> handleChangeLanguage(intent.languageCode)
            SettingIntent.LoadData -> handleLoadData()
        }
    }

    private fun handleLoadData() {
        viewModelScope.launch {
            languageDataStore.languageFlow.collectLatest { code ->
                _state.update { it.copy(languageCode = code) }
            }
        }
    }

    private fun handleChangeLanguage(languageCode: String) {
        viewModelScope.launch {
            languageDataStore.setLanguageCode(languageCode)
            changeLanguage(context = appContext, languageCode = languageCode)
        }
    }

    fun changeLanguage(context: Context, languageCode: String) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(languageCode)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        }

        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
            ?.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
    }
}
