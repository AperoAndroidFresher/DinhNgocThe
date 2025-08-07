package com.example.dinhngocthe.presentation.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.UserRepositoryImpl
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

class LoginViewModel(context: Application) : ViewModel() {
    val tag = "LoginViewModel"
    private val userRepository: UserRepository = UserRepositoryImpl(context)
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginClicked -> login(intent.username, intent.password)

            is LoginIntent.NavigateToSignUp -> viewModelScope.launch { _event.emit(LoginEvent.NavigateToSignUp) }
        }
    }

    fun login(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val user = withContext(Dispatchers.IO) {
                userRepository.getUserByUsernameAndPassword(username, password)
            }
            if (user == null) {
                _event.emit(LoginEvent.ShowError("Sai tên đăng nhập hoặc mật khẩu!"))
            } else {
                CurrentUser.id = user.userId
                _event.emit(LoginEvent.NavigateToHome)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    class LoginViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct LoginViewModelFactory")
        }
    }
}