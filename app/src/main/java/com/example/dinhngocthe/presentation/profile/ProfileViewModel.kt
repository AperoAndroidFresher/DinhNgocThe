package com.example.dinhngocthe.presentation.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.UserRepositoryImpl
import com.example.dinhngocthe.presentation.login.CurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(context: Application) : ViewModel() {
    val tag = "ProfileViewModel"
    private val userRepository = UserRepositoryImpl(context)

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.LoadData -> {
                handleLoadData()
            }

            ProfileIntent.SubmitChange -> {
                handleValidateAndSubmit()
            }

            is ProfileIntent.FullNameChanged -> _state.update { it.copy(fullName = intent.fullName, nameWarning = "") }

            is ProfileIntent.PhoneNumberChanged -> _state.update { it.copy(phoneNumber = intent.phoneNumber, phoneWarning = "") }

            is ProfileIntent.UniversityNameChanged -> _state.update { it.copy(university = intent.universityName, universityWarning = "") }

            is ProfileIntent.DescriptionChanged -> _state.update { it.copy(description = intent.description) }

            is ProfileIntent.AvatarUriChanged -> _state.update { it.copy(avatarUri = intent.avatarUri) }
        }
    }

    private fun handleLoadData() {
        viewModelScope.launch {
            userRepository.getUserByUserId(CurrentUser.id).collectLatest { user ->
                _state.update { it.copy(
                    fullName = user.fullName,
                    phoneNumber = user.phoneNumber,
                    university = user.universityName,
                    avatarUri = user.avatarUri,
                    description = user.description
                ) }
                //Log.d(tag, user.avatarUri.toString())
            }
        }
    }

    private fun handleValidateAndSubmit() {
        val stateValue = _state.value
        var hasError = false

        val nameWarning = when {
            stateValue.fullName.isBlank() -> {
                hasError = true; "Enter your name"
            }
            !stateValue.fullName.all { it.isLetter() || it.isWhitespace() } -> {
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
                        fullName = _state.value.fullName,
                        phoneNumber = _state.value.phoneNumber,
                        universityName = _state.value.university,
                        description = _state.value.description,
                        avatarUri = _state.value.avatarUri.toString(),
                        userId = CurrentUser.id
                    )
                }
                _event.emit(ProfileEvent.ShowSuccessDialog)
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
