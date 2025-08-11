package com.example.dinhngocthe.presentation.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.service.musicstate.MusicState
import com.example.dinhngocthe.service.musicstate.MusicStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MusicPlayerViewModel : ViewModel() {
    private val _state: MutableStateFlow<MusicState> = MutableStateFlow(MusicStateHolder.state.value)
    val state: StateFlow<MusicState> = _state.asStateFlow()

    fun processIntent(intent: MusicPlayerIntent) {
        when(intent) {
            MusicPlayerIntent.LoadData -> {
                handleLoadData()
            }
        }
    }

    private fun handleLoadData() {
        viewModelScope.launch {
            MusicStateHolder.state.collectLatest { musicState ->
                _state.value = musicState
            }
        }
    }
}