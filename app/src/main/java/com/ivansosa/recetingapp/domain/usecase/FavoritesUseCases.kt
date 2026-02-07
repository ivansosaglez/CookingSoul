package com.ivansosa.recetingapp.domain.usecase

import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    operator fun invoke(): Flow<List<MealSummary>> {
        return repository.observeFavorites()
    }
}

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(detail: MealDetail) {
        repository.toggleFavorite(detail)
    }
}

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteFavorite(id)
    }
}
