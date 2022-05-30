package com.mvince.androidcompose.model.data

import android.util.Log
import com.mvince.androidcompose.model.Articletem
import com.mvince.androidcompose.model.response.ArticlesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodMenuRemoteSource @Inject constructor(private val foodMenuApi: FoodMenuApi) {

    private var cachedBoissons: List<Articletem>? = null
    private var cachedPlats: List<Articletem>? = null
    private var cachedMenus: List<Articletem>? = null
    private var cachedDessert: List<Articletem>? = null

    suspend fun getAllBoissons(): List<Articletem> = withContext(Dispatchers.IO) {
        var cachedBoissons = cachedBoissons
        if (cachedBoissons == null) {
            cachedBoissons = foodMenuApi.getBoissons().mapCategoriesToItems()
            this@FoodMenuRemoteSource.cachedBoissons = cachedBoissons
        }
        return@withContext cachedBoissons
    }


    suspend fun getAllPlats(): List<Articletem> = withContext(Dispatchers.IO) {
        var cachedPlats = cachedPlats
        if (cachedPlats == null) {
            cachedPlats = foodMenuApi.getPlats().mapCategoriesToItems()
            this@FoodMenuRemoteSource.cachedPlats = cachedPlats
        }
        return@withContext cachedPlats
    }

    suspend fun getAllMenus(): List<Articletem> = withContext(Dispatchers.IO) {
        var cachedMenus = cachedMenus
        if (cachedMenus == null) {
            cachedMenus = foodMenuApi.getMenus().mapCategoriesToItems()
            this@FoodMenuRemoteSource.cachedMenus = cachedMenus
        }
        return@withContext cachedMenus
    }

    suspend fun getAllDesserts(): List<Articletem> = withContext(Dispatchers.IO) {
        var cachedDessert = cachedDessert
        if (cachedDessert == null) {
            cachedDessert = foodMenuApi.getDesserts().mapCategoriesToItems()
            this@FoodMenuRemoteSource.cachedDessert = cachedDessert
        }
        return@withContext cachedDessert
    }



    private fun ArticlesResponse.mapCategoriesToItems(): List<Articletem> {
        return this.data.map { category ->
            Articletem(
                id = category.id,
                nom = category.nom,
                description = category.description,
                prix = category.prix,
                note = category.note,
                url_image = category.url_image
            )
        }
    }



}