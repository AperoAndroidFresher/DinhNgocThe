package com.example.dinhngocthe.data.remote.model

data class TopAlbumsResponse(
    val topalbums: TopAlbumsContainer
)

data class TopAlbumsContainer(
    val album: List<AlbumDto>
)

data class AlbumDto(
    val name: String,
    val artist: ArtistDto,
    val image: List<ImageDto>
)
