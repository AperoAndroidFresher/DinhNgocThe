package com.example.dinhngocthe.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object Playlists {
    val playlists = MutableStateFlow<List<Playlist>>(emptyList())
}
