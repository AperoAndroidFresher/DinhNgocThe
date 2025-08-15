package com.example.dinhngocthe.presentation.setting

sealed interface SettingIntent {
    data class ChangeLanguage(val languageCode: String) : SettingIntent
    data object LoadData : SettingIntent
}