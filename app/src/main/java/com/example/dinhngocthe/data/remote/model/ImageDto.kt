package com.example.dinhngocthe.data.remote.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("#text") val text: String,
    val size: String
)
