package com.ivansosa.recetingapp.data.repository

import com.ivansosa.recetingapp.data.db.FavoriteDao
import com.ivansosa.recetingapp.data.db.FavoriteMealEntity
import com.ivansosa.recetingapp.data.network.TheMealDbApi
import com.ivansosa.recetingapp.data.toDomain
import com.ivansosa.recetingapp.data.toSummary
import com.ivansosa.recetingapp.di.IoDispatcher
import com.ivansosa.recetingapp.domain.model.MealCategory
import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.repository.MealsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val api: TheMealDbApi,
    private val favoriteDao: FavoriteDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MealsRepository {

    override suspend fun searchMeals(query: String): List<MealSummary> = withContext(dispatcher) {
        val response = api.searchMeals(query)
        response.meals?.map { it.toSummary() } ?: emptyList()
    }

    override suspend fun getCategories(): List<MealCategory> = withContext(dispatcher) {
        api.getCategories().categories.map { it.toDomain() }
    }

    override suspend fun getMealsByCategory(category: String): List<MealSummary> = withContext(dispatcher) {
        val response = api.filterByCategory(category)
        response.meals?.map { it.toSummary() } ?: emptyList()
    }

    override suspend fun getMealDetail(id: String): MealDetail = withContext(dispatcher) {
        // Fetch from API
        val response = api.lookupMeal(id)
        val mealDto = response.meals?.firstOrNull() ?: throw Exception("Meal not found")
        var mealDetail = mealDto.toDomain()
        
        // Check favorite status
        val isFav = favoriteDao.observeIsFavorite(id).first()
        mealDetail = mealDetail.copy(isFavorite = isFav)
        
        mealDetail
    }

    override suspend fun getRandomMeal(): MealDetail = withContext(dispatcher) {
        val response = api.randomMeal()
        val mealDto = response.meals?.firstOrNull() ?: throw Exception("No random meal found")
        var mealDetail = mealDto.toDomain()

        val isFav = favoriteDao.observeIsFavorite(mealDetail.id).first()
        mealDetail = mealDetail.copy(isFavorite = isFav)

        mealDetail
    }

    override fun observeFavorites(): Flow<List<MealSummary>> {
        return favoriteDao.observeFavorites().map { entities ->
            entities.map { entity ->
                MealSummary(
                    id = entity.id,
                    name = entity.name,
                    thumbUrl = entity.thumbUrl
                )
            }
        }
    }

    override fun observeIsFavorite(id: String): Flow<Boolean> {
        return favoriteDao.observeIsFavorite(id)
    }

    override suspend fun toggleFavorite(detail: MealDetail) = withContext(dispatcher) {
        val isFav = favoriteDao.observeIsFavorite(detail.id).first()
        if (isFav) {
            favoriteDao.deleteFavoriteById(detail.id)
        } else {
            val entity = FavoriteMealEntity(
                id = detail.id,
                name = detail.name,
                thumbUrl = detail.thumbUrl
            )
            favoriteDao.upsertFavorite(entity)
        }
    }

    override suspend fun deleteFavorite(id: String) = withContext(dispatcher) {
        favoriteDao.deleteFavoriteById(id)
    }
}
