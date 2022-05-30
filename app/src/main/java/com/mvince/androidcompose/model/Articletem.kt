package com.mvince.androidcompose.model

import com.google.gson.annotations.SerializedName

data class Articletem(
    val id: String,
    val nom: String,
    val description: String,
    val prix: String,
    val note: String,
    val url_image: String,
)
