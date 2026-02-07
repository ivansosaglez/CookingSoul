package com.ivansosa.recetingapp.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "idCategory") val idCategory: String,
    @Json(name = "strCategory") val strCategory: String,
    @Json(name = "strCategoryThumb") val strCategoryThumb: String?,
    @Json(name = "strCategoryDescription") val strCategoryDescription: String?
)
