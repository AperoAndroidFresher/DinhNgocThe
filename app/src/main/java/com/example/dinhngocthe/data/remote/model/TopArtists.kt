package com.example.dinhngocthe.data.remote.model

data class TopArtistResponse(
    val artists: ArtistsWrapper
)

data class ArtistsWrapper(
    val artist: List<TopArtistDto>
)

data class TopArtistDto(
    val name: String?,
    val image: List<ImageDto>?
)