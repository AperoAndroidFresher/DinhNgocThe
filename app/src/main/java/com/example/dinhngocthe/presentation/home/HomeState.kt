package com.example.dinhngocthe.presentation.home

import com.example.dinhngocthe.data.local.entities.User
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse

data class HomeState(
    val user: User = User(),
    val topAlbums: TopAlbumsResponse? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)