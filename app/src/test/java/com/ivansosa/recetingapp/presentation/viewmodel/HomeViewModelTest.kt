package com.ivansosa.recetingapp.presentation.viewmodel

import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.usecase.GetCategoriesUseCase
import com.ivansosa.recetingapp.domain.usecase.GetMealDetailUseCase
import com.ivansosa.recetingapp.domain.usecase.SearchMealsUseCase
import com.ivansosa.recetingapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchMealsUseCase = mock(SearchMealsUseCase::class.java)
    private val getCategoriesUseCase = mock(GetCategoriesUseCase::class.java)
    private val getMealDetailUseCase = mock(GetMealDetailUseCase::class.java)
    private val toggleFavoriteUseCase = mock(ToggleFavoriteUseCase::class.java)

    private lateinit var viewModel: HomeViewModel

    @Test
    fun `searchMeals updates state to Success when results found`() = runTest {
        // Arrange
        val query = "Pie"
        val results = listOf(MealSummary("1", "Apple Pie", null))
        `when`(searchMealsUseCase(query)).thenReturn(results)
        
        // Init ViewModel (will trigger loadCategories too, but we ignore that for now)
        viewModel = HomeViewModel(searchMealsUseCase, getCategoriesUseCase, getMealDetailUseCase, toggleFavoriteUseCase)

        // Act
        viewModel.searchMeals(query)

        // Assert
        val state = viewModel.searchState.first()
        assertTrue(state is UiState.Success)
        assertEquals(results, (state as UiState.Success).data)
    }

    @Test
    fun `searchMeals updates state to Empty when no results found`() = runTest {
        // Arrange
        val query = "Unknown"
        `when`(searchMealsUseCase(query)).thenReturn(emptyList())
        
        viewModel = HomeViewModel(searchMealsUseCase, getCategoriesUseCase, getMealDetailUseCase, toggleFavoriteUseCase)

        // Act
        viewModel.searchMeals(query)

        // Assert
        val state = viewModel.searchState.first()
        assertTrue(state is UiState.Empty)
    }
}
