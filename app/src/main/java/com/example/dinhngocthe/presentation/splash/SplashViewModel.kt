package com.example.dinhngocthe.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.datastore.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _event: MutableSharedFlow<SplashEvent> = MutableSharedFlow<SplashEvent>()
    val event: SharedFlow<SplashEvent> = _event.asSharedFlow()

    fun processIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.CheckAutoLogin -> handleCheckAutoLogin()
        }
    }

    private fun handleCheckAutoLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val remembered = userDataStore.isRememberMe()
            val userId = userDataStore.getUserId()

            if (remembered && userId != null) {
                withContext(Dispatchers.Main) {
                    _event.emit(SplashEvent.NavigateToApp(true))
                }
            } else {
                withContext(Dispatchers.Main) {
                    _event.emit(SplashEvent.NavigateToApp(false))
                }
            }
        }
    }
}