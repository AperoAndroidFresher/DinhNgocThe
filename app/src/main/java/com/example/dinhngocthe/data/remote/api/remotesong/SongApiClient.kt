package com.example.dinhngocthe.data.remote.api.remotesong

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SongApiClient {
    private const val BASE_URL = "https://static.apero.vn"
    private val retrofit by lazy { buildRetrofit() }
    private var gsonConfig = GsonBuilder().create()
    fun build(): SongApiService {
        return retrofit.create(SongApiService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .build()
    }
}