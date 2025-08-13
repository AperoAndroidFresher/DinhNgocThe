package com.example.dinhngocthe.data.remote.api.home

import com.example.dinhngocthe.data.remote.api.remotesong.SongApiClient
import com.example.dinhngocthe.data.remote.api.remotesong.SongApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeApiClient {
    private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
    private val retrofit by lazy { buildRetrofit() }
    private var gsonConfig = GsonBuilder().create()

    fun build(): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .build()
    }
}