package com.example.dinhngocthe.presentation.profile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.UserRepository
import com.example.dinhngocthe.presentation.login.CurrentUser
import com.example.dinhngocthe.presentation.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    context: Application
) : ViewModel() {
    val tag = "ProfileViewModel"
    private val userRepository = UserRepository(context)
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                userRepository.getUserById(CurrentUser.id)
            }
            _state.update { it.copy(
                name = user.name,
                phoneNumber = user.phoneNumber,
                university = user.universityName,
                avatarUri = user.avatar,
                description = user.describeYourSelf
            ) }
            Log.d(tag, user.avatar.toString())
        }
    }

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.ToggleEditMode -> _state.update { it.copy(isEditing = !it.isEditing) }
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
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    userRepository.updateProfile(
                        name = _state.value.name,
                        phoneNumber = _state.value.phoneNumber,
                        university = _state.value.university,
                        describe = _state.value.description,
                        avatar = _state.value.avatarUri.toString(),
                        id = CurrentUser.id
                    )
                }
                _event.emit(ProfileEvent.ShowSuccessDialog)
                _state.update { it.copy(isEditing = false) }
            }
        }
    }

    class ProfileViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct ProfileViewModelFactory")
        }
    }
}
