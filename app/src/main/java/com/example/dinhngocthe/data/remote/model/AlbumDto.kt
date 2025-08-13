package com.example.dinhngocthe.data.remote.model

import com.google.gson.annotations.SerializedName


data class TopAlbumsResponse(
    val topAlbums: TopAlbumsContainer
)

data class TopAlbumsContainer(
    val album: List<AlbumDto>
)

data class AlbumDto(
    val name: String,
    val artist: ArtistDto,
    val image: List<ImageDto>
)

data class ArtistDto(val name: String)

data class ImageDto(
    @SerializedName("#text") val text: String,
    val size: String
)
