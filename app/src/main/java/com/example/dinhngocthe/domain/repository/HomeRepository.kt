package com.example.dinhngocthe.domain.repository

import com.example.dinhngocthe.data.remote.model.SongDto
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse

interface HomeRepository {
    fun getTopAlbums(
        onSuccess: (TopAlbumsResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}