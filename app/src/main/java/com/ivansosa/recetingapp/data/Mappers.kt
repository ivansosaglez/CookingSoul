package com.ivansosa.recetingapp.data

import com.ivansosa.recetingapp.data.network.dto.CategoryDto
import com.ivansosa.recetingapp.data.network.dto.MealDto
import com.ivansosa.recetingapp.data.network.dto.MealShortDto
import com.ivansosa.recetingapp.domain.model.IngredientMeasure
import com.ivansosa.recetingapp.domain.model.MealCategory
import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.model.MealSummary

fun MealDto.toDomain(): MealDetail {
    val ingredientsList = mutableListOf<IngredientMeasure>()
    
    // Helper to add if valid
    fun addIfValid(ingredient: String?, measure: String?) {
        if (!ingredient.isNullOrBlank()) {
            ingredientsList.add(IngredientMeasure(ingredient, measure.orEmpty()))
        }
    }

    addIfValid(strIngredient1, strMeasure1)
    addIfValid(strIngredient2, strMeasure2)
    addIfValid(strIngredient3, strMeasure3)
    addIfValid(strIngredient4, strMeasure4)
    addIfValid(strIngredient5, strMeasure5)
    addIfValid(strIngredient6, strMeasure6)
    addIfValid(strIngredient7, strMeasure7)
    addIfValid(strIngredient8, strMeasure8)
    addIfValid(strIngredient9, strMeasure9)
    addIfValid(strIngredient10, strMeasure10)
    addIfValid(strIngredient11, strMeasure11)
    addIfValid(strIngredient12, strMeasure12)
    addIfValid(strIngredient13, strMeasure13)
    addIfValid(strIngredient14, strMeasure14)
    addIfValid(strIngredient15, strMeasure15)
    addIfValid(strIngredient16, strMeasure16)
    addIfValid(strIngredient17, strMeasure17)
    addIfValid(strIngredient18, strMeasure18)
    addIfValid(strIngredient19, strMeasure19)
    addIfValid(strIngredient20, strMeasure20)

    return MealDetail(
        id = idMeal,
        name = strMeal,
        thumbUrl = strMealThumb,
        category = strCategory ?: "Unknown",
        area = strArea ?: "Unknown",
        instructions = strInstructions ?: "",
        ingredients = ingredientsList,
        isFavorite = false,
        tags = strTags
    )
}

fun MealDto.toSummary(): MealSummary {
    return MealSummary(
        id = idMeal,
        name = strMeal,
        thumbUrl = strMealThumb,
        tags = strTags,
        category = strCategory,
        area = strArea
    )
}

fun MealShortDto.toSummary(): MealSummary {
    return MealSummary(
        id = idMeal,
        name = strMeal,
        thumbUrl = strMealThumb
    )
}

fun CategoryDto.toDomain(): MealCategory {
    return MealCategory(
        id = idCategory,
        name = strCategory,
        thumbUrl = strCategoryThumb,
        description = strCategoryDescription
    )
}
