package com.example.dinhngocthe.presentation.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.presentation.library.MusicPlayerLibrary
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

class MusicPlayerViewModel : ViewModel() {
    private val _state: MutableStateFlow<MusicState> = MutableStateFlow(MusicStateHolder.state.value)
    val state: StateFlow<MusicState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<MusicPlayerEvent> = MutableSharedFlow<MusicPlayerEvent>()
    val event: SharedFlow<MusicPlayerEvent> = _event.asSharedFlow()

    fun processIntent(intent: MusicPlayerIntent) {
        when(intent) {
            MusicPlayerIntent.LoadData -> {
                handleLoadData()
            }

            MusicPlayerIntent.CloseMusic -> {
                handleCloseMusic()
            }

            MusicPlayerIntent.PlayPauseMusic -> {
                handlePlayPauseMusic()
            }

            MusicPlayerIntent.NextMusic -> {
                handleNextMusic()
            }

            MusicPlayerIntent.PreviousMusic -> {
                handlePreviousMusic()
            }
        }
    }

    private fun handlePreviousMusic() {
        viewModelScope.launch {
            _event.emit(MusicPlayerEvent.PreviousMusic)
        }
    }

    private fun handleNextMusic() {
        viewModelScope.launch {
            _event.emit(MusicPlayerEvent.NextMusic)
        }
    }

    private fun handlePlayPauseMusic() {
        viewModelScope.launch {
            _event.emit(MusicPlayerEvent.PlayPauseMusic)
        }
        MusicPlayerLibrary.playPauseMusic()
    }

    private fun handleCloseMusic() {
        viewModelScope.launch {
            _event.emit(MusicPlayerEvent.CloseMusic)
        }
        MusicPlayerLibrary.stopMusic()
    }

    private fun handleLoadData() {
        viewModelScope.launch {
            MusicStateHolder.state.collectLatest { musicState ->
                _state.value = musicState
            }
        }
    }
}