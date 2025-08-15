package com.example.dinhngocthe.data.remote.api.remotesong

import com.example.dinhngocthe.data.remote.model.SongDto
import retrofit2.Call
import retrofit2.http.GET

interface SongApiService {
    @GET("techtrek/Remote_audio.json")
    fun getSongs(): Call<List<SongDto>>
}