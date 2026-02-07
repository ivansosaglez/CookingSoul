package com.ivansosa.recetingapp.domain.usecase

import com.ivansosa.recetingapp.domain.model.MealCategory
import com.ivansosa.recetingapp.domain.repository.MealsRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(): List<MealCategory> {
        return repository.getCategories()
    }
}
