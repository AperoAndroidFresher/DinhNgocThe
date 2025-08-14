package com.example.dinhngocthe.presentation.home

import com.example.dinhngocthe.data.local.entities.User
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse
import com.example.dinhngocthe.data.remote.model.TopArtistResponse
import com.example.dinhngocthe.data.remote.model.TopTracksDto
import com.example.dinhngocthe.data.remote.model.TopTracksResponse

data class HomeState(
    val user: User = User(),
    val topAlbums: TopAlbumsResponse? = null,
    val topTracks: TopTracksResponse? = null,
    val topArtists: TopArtistResponse? = null,
    val isLoading: Boolean = true,
    val error: String = ""
)