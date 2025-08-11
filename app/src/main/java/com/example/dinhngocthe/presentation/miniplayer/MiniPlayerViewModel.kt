package com.example.dinhngocthe.presentation.miniplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.service.musicstate.MusicState
import com.example.dinhngocthe.service.musicstate.MusicStateHolder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MiniPlayerViewModel : ViewModel() {
    private val _state: MutableStateFlow<MusicState> = MutableStateFlow(MusicStateHolder.state.value)
    val state: StateFlow<MusicState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<MiniPlayerEvent> = MutableSharedFlow<MiniPlayerEvent>()
    val event: SharedFlow<MiniPlayerEvent> = _event.asSharedFlow()

    fun processIntent(intent: MiniPlayerIntent) {
        when(intent) {
            MiniPlayerIntent.LoadData -> {
                handleLoadData()
            }

            MiniPlayerIntent.PlayPauseMusic -> {
                handlePlayPauseMusic()
            }

            MiniPlayerIntent.CloseMusic -> {
                handleCloseMusic()
            }
        }
    }

    private fun handleCloseMusic() {
        viewModelScope.launch {
            _event.emit(MiniPlayerEvent.CloseMusic)
        }
    }

    private fun handlePlayPauseMusic() {
        viewModelScope.launch {
            _event.emit(MiniPlayerEvent.PlayPauseMusic)
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