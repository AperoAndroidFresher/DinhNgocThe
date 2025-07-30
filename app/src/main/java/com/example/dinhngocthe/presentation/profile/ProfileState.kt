package com.example.dinhngocthe.presentation.profile

import android.net.Uri

data class ProfileState(
    val name: String = "",
    val phoneNumber: String = "",
    val university: String = "",
    val description: String = "",
    val avatarUri: Uri? = null,
    val nameWarning: String = "",
    val phoneWarning: String = "",
    val universityWarning: String = "",
    val isEditing: Boolean = false,
    val isDarkTheme: Boolean = false,
    val showSuccessDialog: Boolean = false
)
