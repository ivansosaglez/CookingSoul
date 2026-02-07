package com.ivansosa.recetingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivansosa.recetingapp.domain.model.MealCategory
import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.usecase.GetCategoriesUseCase
import com.ivansosa.recetingapp.domain.usecase.GetMealDetailUseCase
import com.ivansosa.recetingapp.domain.usecase.SearchMealsUseCase
import com.ivansosa.recetingapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchMealsUseCase: SearchMealsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealDetailUseCase: GetMealDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow<UiState<List<MealSummary>>>(UiState.Empty)
    val searchState: StateFlow<UiState<List<MealSummary>>> = _searchState.asStateFlow()

    private val _categoriesState = MutableStateFlow<UiState<List<MealCategory>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<MealCategory>>> = _categoriesState.asStateFlow()

    private val _homeCategoriesState = MutableStateFlow<UiState<List<MealCategory>>>(UiState.Loading)
    val homeCategoriesState: StateFlow<UiState<List<MealCategory>>> = _homeCategoriesState.asStateFlow()

    private val _randomMealState = MutableStateFlow<UiState<MealDetail>>(UiState.Empty)
    val randomMealState: StateFlow<UiState<MealDetail>> = _randomMealState.asStateFlow()

    private val _recommendedState = MutableStateFlow<UiState<List<MealSummary>>>(UiState.Loading)
    val recommendedState: StateFlow<UiState<List<MealSummary>>> = _recommendedState.asStateFlow()

    init {
        loadCategories()
        loadRecommended()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = UiState.Loading
            _homeCategoriesState.value = UiState.Loading
            try {
                val categories = getCategoriesUseCase()
                _categoriesState.value = UiState.Success(categories)
                // Randomly select 6 categories for home screen
                _homeCategoriesState.value = UiState.Success(categories.shuffled().take(6))
            } catch (e: Exception) {
                val error = e.message ?: "Unknown error"
                _categoriesState.value = UiState.Error(error)
                _homeCategoriesState.value = UiState.Error(error)
            }
        }
    }

    private fun loadRecommended() {
        viewModelScope.launch {
            _recommendedState.value = UiState.Loading
            try {
                // Using "Chicken" as a default recommendation source
                val results = searchMealsUseCase("Chicken") 
                if (results.isEmpty()) {
                    _recommendedState.value = UiState.Empty
                } else {
                    // Take 10 random items if possible, or just first 10
                    val recommended = results.shuffled().take(10)
                    _recommendedState.value = UiState.Success(recommended)
                }
            } catch (e: Exception) {
                _recommendedState.value = UiState.Error(e.message ?: "Failed to load recommendations")
            }
        }
    }

    fun searchMeals(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchState.value = UiState.Empty
            return
        }
        viewModelScope.launch {
            _searchState.value = UiState.Loading
            try {
                val results = searchMealsUseCase(query)
                if (results.isEmpty()) {
                    _searchState.value = UiState.Empty
                } else {
                    _searchState.value = UiState.Success(results)
                }
            } catch (e: Exception) {
                _searchState.value = UiState.Error(e.message ?: "Search failed")
            }
        }
    }

    fun generateRandomMeal() {
        viewModelScope.launch {
            _randomMealState.value = UiState.Loading
            try {
                val meal = getMealDetailUseCase.getRandom()
                _randomMealState.value = UiState.Success(meal)
            } catch (e: Exception) {
                _randomMealState.value = UiState.Error("Failed to generate random meal")
            }
        }
    }

    fun clearRandomMealState() {
        _randomMealState.value = UiState.Empty
    }
    fun toggleFavorite(meal: MealSummary) {
        viewModelScope.launch {
            // Optimistic update
            val currentSearch = _searchState.value
            if (currentSearch is UiState.Success) {
                _searchState.value = UiState.Success(
                    currentSearch.data.map {
                        if (it.id == meal.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                )
            }
            
             val currentRecommended = _recommendedState.value
            if (currentRecommended is UiState.Success) {
                _recommendedState.value = UiState.Success(
                    currentRecommended.data.map {
                        if (it.id == meal.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                )
            }

            try {
                toggleFavoriteUseCase(meal)
            } catch (e: Exception) {
                // Revert optimistic update if needed, but for now just log or ignore
            }
        }
    }
}
