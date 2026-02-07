package com.ivansosa.recetingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.usecase.ToggleFavoriteUseCase
import com.ivansosa.recetingapp.domain.usecase.ObserveFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    observeFavoritesUseCase: ObserveFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val uiState: StateFlow<UiState<List<MealSummary>>> = observeFavoritesUseCase()
        .map { meals ->
            if (meals.isEmpty()) UiState.Empty else UiState.Success(meals)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun toggleFavorite(meal: MealSummary) {
        viewModelScope.launch {
            toggleFavoriteUseCase(meal)
        }
    }
}
