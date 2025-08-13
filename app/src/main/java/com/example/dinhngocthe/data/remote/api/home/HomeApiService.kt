package com.example.dinhngocthe.data.remote.api.home

import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET(".")
    fun getTopAlbums(
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("method") method: String = "artist.getTopAlbums",
        @Query("mbid") mbid: String
    ): Call<TopAlbumsResponse>
}