package com.mvince.androidcompose.ui.feature.food

import com.mvince.androidcompose.model.Articletem
import com.mvince.androidcompose.model.Categories
import java.util.*


class FoodCategoriesContract {

    data class State(
        val categories: List<Articletem> = listOf(),
        var isLoading: Boolean = false,
        val test : Categories,
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}