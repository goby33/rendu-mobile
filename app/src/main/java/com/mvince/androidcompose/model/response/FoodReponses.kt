package com.mvince.androidcompose.model.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(val data: List<ArticleResponse>)


data class ArticleResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nom") val nom: String,
    @SerializedName("description") val description: String,
    @SerializedName("prix") val prix: String,
    @SerializedName("note") val note: String,
    @SerializedName("url_image") val url_image: String,
)

