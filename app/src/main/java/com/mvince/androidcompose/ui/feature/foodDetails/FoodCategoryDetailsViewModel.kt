package com.mvince.androidcompose.ui.feature.foodDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.mvince.androidcompose.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodCategoryDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(
        FoodCategoryDetailsContract.State(
            ""
        )
    )
        private set

    init {
        viewModelScope.launch {
            val categoryId = stateHandle.get<String>(NavigationKeys.Arg.FOOD_ID)
                ?: throw IllegalStateException("No categoryId was passed to destination.")
            state = state.copy(category = categoryId)
        }
    }

}
