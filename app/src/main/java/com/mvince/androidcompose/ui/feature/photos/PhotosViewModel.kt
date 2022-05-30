package com.mvince.androidcompose.ui.feature.photos

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.androidcompose.model.Photos
import com.mvince.androidcompose.model.State
import com.mvince.androidcompose.repositories.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
): ViewModel()
{


    private val mState = MutableStateFlow<State<Photos>>(State.loading())
    val state: StateFlow<State<Photos>>
        get() = mState


    fun addPost(post: Photos) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(post).collect {
                mState.value = it
            }
        }
    }

    fun addPhotoStorage(data: Bitmap) {
        repository.addPhotoStorage(data);
    }
}