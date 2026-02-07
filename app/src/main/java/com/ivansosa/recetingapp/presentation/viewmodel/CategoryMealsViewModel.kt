package com.ivansosa.recetingapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.usecase.GetMealsByCategoryUseCase
import com.ivansosa.recetingapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryMealsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val categoryName: String = checkNotNull(savedStateHandle["categoryName"])
    
    private val _uiState = MutableStateFlow<UiState<List<MealSummary>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MealSummary>>> = _uiState.asStateFlow()

    init {
        loadMeals()
    }

    private fun loadMeals() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val meals = getMealsByCategoryUseCase(categoryName)
                if (meals.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _uiState.value = UiState.Success(meals)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to load meals")
            }
        }
    }

    fun toggleFavorite(meal: MealSummary) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is UiState.Success) {
                 _uiState.value = UiState.Success(
                    currentState.data.map {
                        if (it.id == meal.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                )
                try {
                    toggleFavoriteUseCase(meal)
                } catch (e: Exception) {
                    // Revert if needed
                }
            }
        }
    }
}
