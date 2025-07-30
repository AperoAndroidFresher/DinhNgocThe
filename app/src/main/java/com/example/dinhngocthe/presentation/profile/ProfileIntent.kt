package com.example.dinhngocthe.presentation.profile

import android.net.Uri

sealed interface ProfileIntent {
    data object ToggleEditMode : ProfileIntent
    data object ChangeTheme : ProfileIntent
    data class NameChanged(val name: String) : ProfileIntent
    data class PhoneNumberChanged(val phoneNumber: String) : ProfileIntent
    data class UniversityChanged(val university: String) : ProfileIntent
    data class DescriptionChanged(val description: String) : ProfileIntent
    data class AvatarChanged(val uri: Uri?) : ProfileIntent
    data object Submit : ProfileIntent
}
