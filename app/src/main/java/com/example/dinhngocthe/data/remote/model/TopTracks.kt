package com.example.dinhngocthe.data.remote.model

data class TopTracksResponse(
    val toptracks: TopTracksDto
)

data class TopTracksDto(
    val track: List<TrackDto> = emptyList()
)

data class TrackDto(
    val name: String?,
    val playcount: String?,
    val artist: ArtistDto?,
    val image: List<ImageDto>?
)

