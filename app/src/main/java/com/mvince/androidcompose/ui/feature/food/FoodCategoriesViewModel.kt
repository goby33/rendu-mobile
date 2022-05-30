package com.mvince.androidcompose.ui.feature.food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvince.androidcompose.model.Articletem
import com.mvince.androidcompose.model.Categories
import com.mvince.androidcompose.model.data.FoodMenuRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodCategoriesViewModel @Inject constructor(private val remoteSource: FoodMenuRemoteSource) :
    ViewModel() {

    var state by mutableStateOf(
        FoodCategoriesContract.State(
            categories = listOf(),
            isLoading = true,
           test = Categories(),
        )
    )
        private set

    var effects = Channel<FoodCategoriesContract.Effect>(UNLIMITED)
        private set

    init {
        viewModelScope.launch { getFoodCategories() }
    }

    private suspend fun getFoodCategories() {
        val obj = Categories(
            boissons = remoteSource.getAllBoissons(),
            plats = remoteSource.getAllPlats(),
            menus = remoteSource.getAllMenus(),
            desserts = remoteSource.getAllDesserts()
        )
        viewModelScope.launch {
            state = state.copy(
                categories = obj.menus,
                test = obj,
                isLoading = false)
            effects.send(FoodCategoriesContract.Effect.DataWasLoaded)
        }
    }

}



