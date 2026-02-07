package com.ivansosa.recetingapp.domain.usecase

import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.repository.MealsRepository
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(query: String): List<MealSummary> {
        return repository.searchMeals(query)
    }
}
