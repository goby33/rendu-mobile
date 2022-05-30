package com.mvince.androidcompose.ui.feature.photos

import com.mvince.androidcompose.model.Photos

class PhotosContract {
    data class State(
        val posts: List<Photos> = listOf(),
        val isLoading: Boolean = false
    )
}