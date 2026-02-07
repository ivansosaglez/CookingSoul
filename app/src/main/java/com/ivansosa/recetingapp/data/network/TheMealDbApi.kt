package com.ivansosa.recetingapp.data.network

import com.ivansosa.recetingapp.data.network.dto.CategoriesResponse
import com.ivansosa.recetingapp.data.network.dto.MealsResponse
import com.ivansosa.recetingapp.data.network.dto.MealsShortResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbApi {
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealsResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealsShortResponse

    @GET("lookup.php")
    suspend fun lookupMeal(@Query("i") id: String): MealsResponse

    @GET("random.php")
    suspend fun randomMeal(): MealsResponse
}
