package com.example.dinhngocthe.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    @SerializedName("toptracks") val topTracks: TopTracksDto
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

