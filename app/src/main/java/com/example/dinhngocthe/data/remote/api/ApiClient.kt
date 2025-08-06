package com.example.dinhngocthe.data.remote.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://static.apero.vn"
    private val retrofit by lazy { buildRetrofit() }
    private var gsonConfig = GsonBuilder().create()
    fun build(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .build()
    }
}