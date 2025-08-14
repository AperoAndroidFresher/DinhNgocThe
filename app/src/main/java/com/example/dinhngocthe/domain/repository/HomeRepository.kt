package com.example.dinhngocthe.domain.repository

import com.example.dinhngocthe.data.remote.model.SongDto
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse
import com.example.dinhngocthe.data.remote.model.TopArtistResponse
import com.example.dinhngocthe.data.remote.model.TopTracksDto
import com.example.dinhngocthe.data.remote.model.TopTracksResponse

interface HomeRepository {
    fun getTopAlbums(
        onSuccess: (TopAlbumsResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getTopTracks(
        onSuccess: (TopTracksResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getTopArtists(
        onSuccess: (TopArtistResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}