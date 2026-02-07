package com.ivansosa.recetingapp.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object CategoryMeals : Screen("category/{categoryName}") {
        fun createRoute(categoryName: String) = "category/$categoryName"
    }
    data object MealDetail : Screen("detail/{mealId}") {
        fun createRoute(mealId: String) = "detail/$mealId"
    }
}
