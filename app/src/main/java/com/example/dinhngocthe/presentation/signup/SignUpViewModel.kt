package com.example.dinhngocthe.presentation.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.entities.User
import com.example.dinhngocthe.data.repository.UserRepositoryImpl
import com.example.dinhngocthe.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(context: Application) : ViewModel() {
    val tag = "SignUpViewModel"
    private val userRepository: UserRepository = UserRepositoryImpl(context)

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SignUpEvent>(replay = 0)
    val event = _event.asSharedFlow()

    fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.SignUpClicked -> {
                handleSignUp(
                    username = intent.username,
                    password = intent.password,
                    confirmPassword = intent.confirmPassword,
                    email = intent.email
                )
            }

            SignUpIntent.NavigateToLogin -> viewModelScope.launch { _event.emit(SignUpEvent.NavigateToLogin) }
        }
    }

    private fun handleSignUp(
        username: String,
        password: String,
        confirmPassword: String,
        email: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val hasError = validateForm(
                username,
                password,
                confirmPassword,
                email
            )
            if (!hasError) {
                val user = User(
                    username = username,
                    password = password,
                    email = email
                )
                userRepository.insertUser(user)
                _event.emit(SignUpEvent.SignUpSuccess)
                //Log.d(tag, "signup success!")
            }
        }
    }

    private fun validateForm(
        username: String,
        password: String,
        confirmPassword: String,
        email: String
    ) : Boolean {
        val username = username.trim()
        val password = password.trim()
        val confirm  = confirmPassword.trim()
        val email    = email.trim()

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

        return hasError
    }

    class SignUpViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SignUpViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct SignUpViewModelFactory")
        }
    }
}
