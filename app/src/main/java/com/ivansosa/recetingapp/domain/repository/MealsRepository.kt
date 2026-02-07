package com.ivansosa.recetingapp.domain.repository

import com.ivansosa.recetingapp.domain.model.MealCategory
import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.model.MealSummary
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun searchMeals(query: String): List<MealSummary>
    suspend fun getCategories(): List<MealCategory>
    suspend fun getMealsByCategory(category: String): List<MealSummary>
    suspend fun getMealDetail(id: String): MealDetail
    suspend fun getRandomMeal(): MealDetail
    
    fun observeFavorites(): Flow<List<MealSummary>>
    fun observeIsFavorite(id: String): Flow<Boolean>
    suspend fun toggleFavorite(detail: MealDetail)
    suspend fun deleteFavorite(id: String)
}
