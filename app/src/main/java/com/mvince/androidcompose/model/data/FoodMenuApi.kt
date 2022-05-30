package com.mvince.androidcompose.model.data

import com.mvince.androidcompose.model.response.ArticlesResponse
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodMenuApi @Inject constructor(private val service: Service) {

    suspend fun getBoissons(): ArticlesResponse = service.getBoissons()
    suspend fun getPlats(): ArticlesResponse = service.getPlats()
    suspend fun getMenus(): ArticlesResponse = service.getMenus()
    suspend fun getDesserts(): ArticlesResponse = service.getDesserts()

    interface Service {
        @GET("boisson/all")
        suspend fun getBoissons(): ArticlesResponse


        @GET("plat/all")
        suspend fun getPlats(): ArticlesResponse


        @GET("menu/all")
        suspend fun getMenus(): ArticlesResponse

        @GET("dessert/all")
        suspend fun getDesserts(): ArticlesResponse
    }

    companion object {
        const val API_URL = "https://morning-escarpment-57263.herokuapp.com/v1/"
    }
}


