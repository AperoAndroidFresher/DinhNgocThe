package com.example.dinhngocthe.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.preferences.UserPreferences
import com.example.dinhngocthe.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    val tag = "LoginViewModel"
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginClicked -> handleLogin(intent.username, intent.password, intent.rememberMe)

            is LoginIntent.NavigateToSignUp -> viewModelScope.launch { _event.emit(LoginEvent.NavigateToSignUp) }
        }
    }

    private fun handleLogin(
        username: String,
        password: String,
        rememberMe: Boolean
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val user = withContext(Dispatchers.IO) {
                userRepository.getUserByUsernameAndPassword(username, password)
            }

            if (user == null) {
                _event.emit(LoginEvent.ShowError("Sai tên đăng nhập hoặc mật khẩu!"))
            } else {
                userPreferences.setUserId(user.userId)
                userPreferences.setRememberMe(rememberMe)
                _event.emit(LoginEvent.NavigateToHome)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun checkAutoLogin() {
        val remembered = userPreferences.isRememberMe()
        val userId = userPreferences.getUserId()
        if (remembered == true && userId != null) {
            viewModelScope.launch {
                _event.emit(LoginEvent.NavigateToHome)
            }
        }
    }
}