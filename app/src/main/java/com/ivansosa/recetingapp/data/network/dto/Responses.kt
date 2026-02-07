package com.ivansosa.recetingapp.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealsResponse(
    @Json(name = "meals") val meals: List<MealDto>?
)

@JsonClass(generateAdapter = true)
data class MealsShortResponse(
    @Json(name = "meals") val meals: List<MealShortDto>?
)

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    @Json(name = "categories") val categories: List<CategoryDto>
)
