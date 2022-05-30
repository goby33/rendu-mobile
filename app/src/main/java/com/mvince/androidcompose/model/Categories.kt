package com.mvince.androidcompose.model

data class Categories(
    val boissons: List<Articletem> = listOf(),
    val plats: List<Articletem> = listOf(),
    val menus: List<Articletem> = listOf(),
    val desserts: List<Articletem> = listOf(),
)