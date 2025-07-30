package com.example.dinhngocthe.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.ToggleEditMode -> {
                _state.update { it.copy(isEditing = !it.isEditing) }
            }
            is ProfileIntent.ChangeTheme -> {
                viewModelScope.launch { _event.emit(ProfileEvent.ChangeTheme) }
            }
            is ProfileIntent.NameChanged -> _state.update { it.copy(name = intent.name, nameWarning = "") }
            is ProfileIntent.PhoneNumberChanged -> _state.update { it.copy(phoneNumber = intent.phoneNumber, phoneWarning = "") }
            is ProfileIntent.UniversityChanged -> _state.update { it.copy(university = intent.university, universityWarning = "") }
            is ProfileIntent.DescriptionChanged -> _state.update { it.copy(description = intent.description) }
            is ProfileIntent.AvatarChanged -> _state.update { it.copy(avatarUri = intent.uri) }
            is ProfileIntent.Submit -> validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val stateValue = _state.value
        var hasError = false

        val nameWarning = when {
            stateValue.name.isBlank() -> {
                hasError = true; "Enter your name"
            }
            !stateValue.name.all { it.isLetter() || it.isWhitespace() } -> {
                hasError = true; "Invalid format"
            }
            else -> ""
        }

        val phoneWarning = when {
            stateValue.phoneNumber.isBlank() -> {
                hasError = true; "Enter phone number"
            }
            !stateValue.phoneNumber.all { it.isDigit() } -> {
                hasError = true; "Invalid format"
            }
            else -> ""
        }

        val universityWarning = when {
            stateValue.university.isBlank() -> {
                hasError = true; "Enter university name"
            }
            !stateValue.university.all { it.isLetter() || it.isWhitespace() } -> {
                hasError = true; "Invalid format"
            }
            else -> ""
        }

        _state.update {
            it.copy(
                nameWarning = nameWarning,
                phoneWarning = phoneWarning,
                universityWarning = universityWarning
            )
        }

        if (!hasError) {
            viewModelScope.launch { _event.emit(ProfileEvent.ShowSuccessDialog) }
        }
    }
}
