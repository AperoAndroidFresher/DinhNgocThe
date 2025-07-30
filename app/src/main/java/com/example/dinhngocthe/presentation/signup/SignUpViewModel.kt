package com.example.dinhngocthe.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.model.User
import com.example.dinhngocthe.model.Users
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SignUpEvent>(replay = 0)
    val event = _event.asSharedFlow()

    fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UsernameChanged ->
                _state.update { it.copy(username = intent.username, usernameError = null) }

            is SignUpIntent.PasswordChanged ->
                _state.update { it.copy(password = intent.password, passwordError = null) }

            is SignUpIntent.ConfirmPasswordChanged ->
                _state.update { it.copy(confirmPassword = intent.confirmPassword, confirmPasswordError = null) }

            is SignUpIntent.EmailChanged ->
                _state.update { it.copy(email = intent.email, emailError = null) }

            SignUpIntent.TogglePasswordVisible ->
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }

            SignUpIntent.ToggleConfirmPasswordVisible ->
                _state.update { it.copy(confirmPasswordVisible = !it.confirmPasswordVisible) }

            SignUpIntent.SignUpClicked -> signUp()

            SignUpIntent.BackToLogin ->
                viewModelScope.launch { _event.emit(SignUpEvent.BackToLogin) }
        }
    }

    private fun signUp() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val username = state.value.username.trim()
        val password = state.value.password.trim()
        val confirm  = state.value.confirmPassword.trim()
        val email    = state.value.email.trim()

        val userNameRegex = Regex("^[a-z0-9]+\$", RegexOption.IGNORE_CASE)
        val passwordRegex = Regex("^[a-zA-Z0-9]+\$")
        val emailRegex    = Regex("^[a-z0-9._-]+@apero\\.vn\$", RegexOption.IGNORE_CASE)

        var uErr: String? = null
        var pErr: String? = null
        var cErr: String? = null
        var eErr: String? = null

        when {
            username.isBlank() -> uErr = "Enter user name"
            !userNameRegex.matches(username) -> uErr = "Only a–z and 0–9 allowed, no spaces"
            username.contains(' ') || username.contains('\t') -> uErr = "Username cannot contain spaces or tabs"
        }

        when {
            password.isBlank() -> pErr = "Enter password"
            !passwordRegex.matches(password) -> pErr = "Only a–z, A–Z and 0–9 allowed, no spaces"
            password.contains(' ') || password.contains('\t') -> pErr = "Password cannot contain spaces or tabs"
        }

        when {
            confirm.isBlank() -> cErr = "Confirm your password"
            confirm != password -> cErr = "Passwords do not match"
        }

        when {
            email.isBlank() -> eErr = "Enter email"
            !emailRegex.matches(email) -> eErr = "Invalid email format. Must end with @apero.vn"
            email.contains(' ') || email.contains('\t') -> eErr = "Email cannot contain spaces or tabs"
        }

        _state.update {
            it.copy(
                usernameError = uErr,
                passwordError = pErr,
                confirmPasswordError = cErr,
                emailError = eErr
            )
        }

        val hasError = uErr != null || pErr != null || cErr != null || eErr != null

        if (!hasError) {
            Users.users.add(User(username, password, email)) // fake DB
            _event.emit(SignUpEvent.SignUpSuccess)
        }

        _state.update { it.copy(isLoading = false) }
    }
}
