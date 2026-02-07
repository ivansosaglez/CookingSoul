package com.ivansosa.recetingapp.domain.model

data class MealSummary(
    val id: String,
    val name: String,
    val thumbUrl: String?,
    val isFavorite: Boolean = false
)

data class MealDetail(
    val id: String,
    val name: String,
    val thumbUrl: String?,
    val category: String,
    val area: String,
    val instructions: String,
    val ingredients: List<IngredientMeasure>,
    val isFavorite: Boolean = false
)

data class IngredientMeasure(
    val ingredient: String,
    val measure: String
)

data class MealCategory(
    val id: String,
    val name: String,
    val thumbUrl: String?,
    val description: String?
)
