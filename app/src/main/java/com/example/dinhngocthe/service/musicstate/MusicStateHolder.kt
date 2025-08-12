package com.example.dinhngocthe.service.musicstate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MusicStateHolder {
    private val _state: MutableStateFlow<MusicState> = MutableStateFlow(MusicState())
    val state: StateFlow<MusicState> = _state.asStateFlow()

    fun updateState(state: MusicState) {
        _state.value = state
    }

    fun updateIsPlayingState(isPlaying: Boolean) {
        _state.update { it.copy(isPlaying = isPlaying) }
    }

    fun closePlayMusic() {
        _state.value = MusicState()
    }
}