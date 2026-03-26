package com.ivansosa.recetingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ivansosa.recetingapp.presentation.screens.CategoriesScreen
import com.ivansosa.recetingapp.presentation.screens.CategoryMealsScreen
import com.ivansosa.recetingapp.presentation.screens.FavoritesScreen
import com.ivansosa.recetingapp.presentation.screens.HomeScreen
import com.ivansosa.recetingapp.presentation.screens.MealDetailScreen
import com.ivansosa.recetingapp.presentation.screens.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(onSplashComplete = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCategory = { categoryName ->
                    navController.navigate(Screen.CategoryMeals.createRoute(categoryName))
                },
                onNavigateToDetail = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                },
                onNavigateToAllCategories = {
                    navController.navigate(Screen.CategoriesList.route)
                }
            )
        }

        composable(route = Screen.CategoriesList.route) {
            CategoriesScreen(
                onNavigateUp = { navController.navigateUp() },
                onCategoryClick = { categoryName ->
                     navController.navigate(Screen.CategoryMeals.createRoute(categoryName))
                }
            )
        }

        composable(
            route = Screen.CategoryMeals.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryMealsScreen(
                categoryName = categoryName,
                onNavigateUp = { navController.navigateUp() },
                onNavigateToDetail = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                }
            )
        }

        composable(
            route = Screen.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) {
            MealDetailScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onNavigateUp = { navController.navigateUp() },
                onNavigateToDetail = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                },
                onNavigateToCategories = {
                    navController.navigate(Screen.CategoriesList.route)
                }
            )
        }
    }
}
