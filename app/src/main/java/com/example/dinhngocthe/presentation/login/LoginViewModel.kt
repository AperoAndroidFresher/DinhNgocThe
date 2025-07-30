package com.example.dinhngocthe.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.model.Users
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UsernameChanged -> {
                _state.update { it.copy(username = intent.username, error = "") }
            }

            is LoginIntent.PasswordChanged -> {
                _state.update { it.copy(password = intent.password, error = "") }
            }

            is LoginIntent.TogglePasswordVisible -> {
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            }

            is LoginIntent.RememberMeChecked -> {
                _state.update { it.copy(rememberMe = intent.checked) }
            }

            is LoginIntent.LoginClicked -> login()

            is LoginIntent.NavigateToSignUp -> viewModelScope.launch { _event.emit(LoginEvent.NavigateToSignUp) }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }
            val userFound = Users.users.find { it.userName == _state.value.username && it.passWord == _state.value.password }
            if (userFound != null) {
                _event.emit(LoginEvent.NavigateToHome)
            } else {
                _state.update { it.copy(error = "Sai tên đăng nhập hoặc mật khẩu") }
                _event.emit(LoginEvent.ShowError(_state.value.error))
            }
            _state.update { it.copy(isLoading = false ) }
        }
    }


}