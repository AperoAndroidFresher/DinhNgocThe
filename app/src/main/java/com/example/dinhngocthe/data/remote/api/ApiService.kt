package com.example.dinhngocthe.data.remote.api

import com.example.dinhngocthe.data.remote.model.SongDto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("techtrek/Remote_audio.json")
    fun getSongs(): Call<List<SongDto>>
}