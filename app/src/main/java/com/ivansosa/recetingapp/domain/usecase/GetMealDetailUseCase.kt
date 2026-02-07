package com.ivansosa.recetingapp.domain.usecase

import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.repository.MealsRepository
import javax.inject.Inject

class GetMealDetailUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(id: String): MealDetail {
        return repository.getMealDetail(id)
    }
    
    suspend fun getRandom(): MealDetail {
        return repository.getRandomMeal()
    }
}
