package com.example.dinhngocthe.data.repository

import android.util.Log
import com.example.dinhngocthe.data.remote.api.home.HomeApiClient
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse
import com.example.dinhngocthe.data.remote.model.TopArtistResponse
import com.example.dinhngocthe.data.remote.model.TopTracksDto
import com.example.dinhngocthe.data.remote.model.TopTracksResponse
import com.example.dinhngocthe.domain.repository.HomeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepositoryImpl : HomeRepository {
    override fun getTopArtists(
        onSuccess: (TopArtistResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = HomeApiClient.build().getTopArtists(
            apiKey = "e65449d181214f936368984d4f4d4ae8",
            format = "json",
            method = "chart.gettopartists",
            mbid = ""
        )
        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(
                call: Call<TopArtistResponse>,
                response: Response<TopArtistResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val topArtists = response.body()
                        topArtists?.let { onSuccess(it) }
                    }

                    response.code() == 400 -> {
                        onFailure(Exception("Bad request"))
                    }

                    response.code() == 401 -> {
                        onFailure(Exception("Unauthorized"))
                    }

                    response.code() == 403 -> {
                        onFailure(Exception("Forbidden"))
                    }

                    response.code() == 404 -> {
                        onFailure(Exception("Not Found"))
                    }

                    response.code() == 500 -> {
                        onFailure(Exception("Internal Server Error"))
                    }

                    else -> {
                        onFailure(Exception("Unknown Error"))
                    }
                }
            }

            override fun onFailure(
                call: Call<TopArtistResponse>,
                t: Throwable
            ) {
                onFailure(t)
            }
        })
    }

    override fun getTopAlbums(
        onSuccess: (TopAlbumsResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = HomeApiClient.build().getTopAlbums(
            apiKey = "e65449d181214f936368984d4f4d4ae8",
            format = "json",
            method = "artist.getTopAlbums",
            mbid = "f9b593e6-4503-414c-99a0-46595ecd2e23"
        )
        call.enqueue(object : Callback<TopAlbumsResponse> {
            override fun onResponse(
                call: Call<TopAlbumsResponse>,
                response: Response<TopAlbumsResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val topAlbums = response.body()
                        topAlbums?.let { onSuccess(it) }
                    }

                    response.code() == 400 -> {
                        onFailure(Exception("Bad request"))
                    }

                    response.code() == 401 -> {
                        onFailure(Exception("Unauthorized"))
                    }

                    response.code() == 403 -> {
                        onFailure(Exception("Forbidden"))
                    }

                    response.code() == 404 -> {
                        onFailure(Exception("Not Found"))
                    }

                    response.code() == 500 -> {
                        onFailure(Exception("Internal Server Error"))
                    }

                    else -> {
                        onFailure(Exception("Unknown Error"))
                    }
                }
            }

            override fun onFailure(
                call: Call<TopAlbumsResponse?>,
                t: Throwable
            ) {
                onFailure(t)
            }
        })
    }

    override fun getTopTracks(
        onSuccess: (TopTracksResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val call = HomeApiClient.build().getTopTracks(
            apiKey = "e65449d181214f936368984d4f4d4ae8",
            format = "json",
            method = "artist.getTopTracks",
            mbid = "f9b593e6-4503-414c-99a0-46595ecd2e23"
        )
        call.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(
                call: Call<TopTracksResponse>,
                response: Response<TopTracksResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val topTracks = response.body()
                        topTracks?.let { onSuccess(it) }
                    }

                    response.code() == 400 -> {
                        onFailure(Exception("Bad request"))
                    }

                    response.code() == 401 -> {
                        onFailure(Exception("Unauthorized"))
                    }

                    response.code() == 403 -> {
                        onFailure(Exception("Forbidden"))
                    }

                    response.code() == 404 -> {
                        onFailure(Exception("Not Found"))
                    }

                    response.code() == 500 -> {
                        onFailure(Exception("Internal Server Error"))
                    }

                    else -> {
                        onFailure(Exception("Unknown Error"))
                    }
                }
            }

            override fun onFailure(
                call: Call<TopTracksResponse>,
                t: Throwable
            ) {
                onFailure(t)
            }
        })
    }
}