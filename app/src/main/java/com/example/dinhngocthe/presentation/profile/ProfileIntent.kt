package com.example.dinhngocthe.presentation.profile

import android.net.Uri

sealed interface ProfileIntent {
    data class FullNameChanged(val fullName: String) : ProfileIntent
    data class PhoneNumberChanged(val phoneNumber: String) : ProfileIntent
    data class UniversityNameChanged(val universityName: String) : ProfileIntent
    data class DescriptionChanged(val description: String) : ProfileIntent
    data class AvatarUriChanged(val avatarUri: Uri?) : ProfileIntent
    data object SubmitChange : ProfileIntent
    data object LoadData : ProfileIntent
    data object LogOut : ProfileIntent
}
