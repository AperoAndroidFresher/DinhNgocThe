package com.example.dinhngocthe.data.repository

import android.util.Log
import com.example.dinhngocthe.data.remote.api.home.HomeApiClient
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse
import com.example.dinhngocthe.domain.repository.HomeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepositoryImpl : HomeRepository {
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
                call: Call<TopAlbumsResponse?>,
                response: Response<TopAlbumsResponse?>
            ) {
                when {
                    response.isSuccessful -> {
                        val songs = response.body()
                        songs?.let { onSuccess(it) }
                    }

                    response.code() == 400 -> {
                        onFailure(Exception("Bad request"))
                        Log.e("HomeRepositoryImpl", "Bad request")
                    }

                    response.code() == 401 -> {
                        onFailure(Exception("Unauthorized"))
                        Log.e("HomeRepositoryImpl", "Unauthorized")
                    }

                    response.code() == 403 -> {
                        onFailure(Exception("Forbidden"))
                        Log.e("HomeRepositoryImpl", "Forbidden")
                    }

                    response.code() == 404 -> {
                        onFailure(Exception("Not Found"))
                        Log.e("HomeRepositoryImpl", "Not Found")
                    }

                    response.code() == 500 -> {
                        onFailure(Exception("Internal Server Error"))
                        Log.e("HomeRepositoryImpl", "Internal Server Error")
                    }

                    else -> {
                        onFailure(Exception("Unknown Error"))
                        Log.e("HomeRepositoryImpl", "Unknown Error")
                    }
                }
            }

            override fun onFailure(
                call: Call<TopAlbumsResponse?>,
                t: Throwable
            ) {
                onFailure(t)
                Log.e("HomeRepositoryImpl", "onFailure: ${t.message}")
            }
        })
    }
}