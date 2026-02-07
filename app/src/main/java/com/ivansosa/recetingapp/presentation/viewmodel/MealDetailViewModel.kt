package com.ivansosa.recetingapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.domain.usecase.GetMealDetailUseCase
import com.ivansosa.recetingapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMealDetailUseCase: GetMealDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val mealId: String = checkNotNull(savedStateHandle["mealId"])

    private val _uiState = MutableStateFlow<UiState<MealDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<MealDetail>> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val detail = getMealDetailUseCase(mealId)
                _uiState.value = UiState.Success(detail)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to load detail")
            }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            viewModelScope.launch {
                val currentDetail = currentState.data
                // Toggle in DB
                toggleFavoriteUseCase(currentDetail)
                // Update UI immediately (optimistic update)
                _uiState.value = UiState.Success(currentDetail.copy(isFavorite = !currentDetail.isFavorite))
            }
        }
    }
}
