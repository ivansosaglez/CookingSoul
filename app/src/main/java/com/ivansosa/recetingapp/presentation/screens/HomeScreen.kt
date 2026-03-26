package com.ivansosa.recetingapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ivansosa.recetingapp.R
import com.ivansosa.recetingapp.domain.model.MealCategory
import com.ivansosa.recetingapp.domain.model.MealSummary
import com.ivansosa.recetingapp.domain.model.MealDetail
import com.ivansosa.recetingapp.presentation.components.AdBannerView
import com.ivansosa.recetingapp.presentation.components.BottomNavigationBar
import com.ivansosa.recetingapp.presentation.components.CustomSearchBar
import com.ivansosa.recetingapp.presentation.components.SectionHeader
import com.ivansosa.recetingapp.presentation.navigation.Screen
import com.ivansosa.recetingapp.presentation.viewmodel.HomeViewModel
import com.ivansosa.recetingapp.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCategory: (String) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToAllCategories: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val categoriesState by viewModel.homeCategoriesState.collectAsStateWithLifecycle()
    val randomMealState by viewModel.randomMealState.collectAsStateWithLifecycle()
    val recommendedState by viewModel.recommendedState.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    HomeScreenContent(
        searchState = searchState,
        categoriesState = categoriesState,
        randomMealState = randomMealState,
        recommendedState = recommendedState,
        query = query,
        onQueryChange = { viewModel.searchMeals(it) },
        onGenerateRandomMeal = { viewModel.generateRandomMeal() },
        onClearRandomMealState = { viewModel.clearRandomMealState() },
        onNavigateToCategory = onNavigateToCategory,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToFavorites = onNavigateToFavorites,
        onNavigateToAllCategories = onNavigateToAllCategories,
        onToggleFavorite = { viewModel.toggleFavorite(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    searchState: UiState<List<MealSummary>>,
    categoriesState: UiState<List<MealCategory>>,
    randomMealState: UiState<MealDetail>,
    recommendedState: UiState<List<MealSummary>>,
    query: String,
    onQueryChange: (String) -> Unit,
    onGenerateRandomMeal: () -> Unit,
    onClearRandomMealState: () -> Unit,
    onNavigateToCategory: (String) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToAllCategories: () -> Unit,
    onToggleFavorite: (MealSummary) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface, fontSize = 20.sp, fontWeight = FontWeight.Normal)) {
                                    append("What are we\n")
                                }
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, fontWeight = FontWeight.Bold)) {
                                    append("cooking today?")
                                }
                            },
                            lineHeight = 28.sp
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Check notifications */ },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = Screen.Home.route,
                onNavigate = { route ->
                    when (route) {
                        Screen.Favorites.route -> onNavigateToFavorites()
                        Screen.CategoriesList.route -> onNavigateToAllCategories()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            CustomSearchBar(
                query = query,
                onQueryChange = onQueryChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Ad Banner
            AdBannerView()

            Spacer(modifier = Modifier.height(12.dp))
            
            androidx.compose.animation.Crossfade(targetState = query.isEmpty(), label = "SearchTransition") { isQueryEmpty ->
                Column {
                    if (isQueryEmpty) {
                        // Surprise Me Button
                        Button(
                            onClick = onGenerateRandomMeal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            // Should use Icon here if available, text for now
                            Text(
                                text = "Random Recipe 🎲",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }

                        // Show Random Meal if available (temporary UX for "Surprise Me")
                        if (randomMealState is UiState.Success) {
                            // Rely on smart cast and explicit type to avoid erasure
                            val meal = (randomMealState as UiState.Success<*>).data as MealDetail

                            // Navigate and then reset state so we don't return here on back press
                            // This is a side-effect, should ideally be in LaunchedEffect but this works for now if safe
                            androidx.compose.runtime.LaunchedEffect(meal) {
                                onNavigateToDetail(meal.id)
                                onClearRandomMealState()
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        SectionHeader(title = "Recommended")
                        // Mocking recommended using Search Results or just static
                        // Since we don't have "Recommended" API, use a default search like "Beef" or empty search state if holds something
                        // For now, let's assume `searchState` holds some initial "Recommended" items if query is empty (HomeViewModel init)
                        if (recommendedState is UiState.Success) {
                            val meals = (recommendedState as UiState.Success).data
                            // Staggered grid mock using Row of Columns
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                // Implementing a rudimentary grid manually inside Column scroll
                                if (meals.isNotEmpty()) {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        meals.filterIndexed { i, _ -> i % 2 == 0 }.forEach { meal ->
                                            RecipeGridCard(
                                                meal = meal,
                                                onClick = { onNavigateToDetail(meal.id) },
                                                onFavoriteClick = { onToggleFavorite(meal) },
                                                isFavorite = meal.isFavorite,
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }
                                    }
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        meals.filterIndexed { i, _ -> i % 2 != 0 }.forEach { meal ->
                                            RecipeGridCard(
                                                meal = meal,
                                                onClick = { onNavigateToDetail(meal.id) },
                                                onFavoriteClick = { onToggleFavorite(meal) },
                                                isFavorite = meal.isFavorite,
                                                modifier = Modifier.padding(bottom = 16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        } else if (recommendedState is UiState.Loading) {
                            LoadingView(Modifier.height(200.dp))
                        }
                    } else {
                        // Search Results
                        SectionHeader(title = "Search Results")
                        when (searchState) {
                            is UiState.Success -> {
                                val meals = (searchState as UiState.Success).data
                                // Using a simple column for scrollable content inside the scrollable column is problematic
                                // Ideally we use LazyColumn for the whole screen or switch layout.
                                // For MVP visual:
                                meals.take(5).forEach { meal ->
                                    RecipeGridCard(
                                        meal = meal,
                                        onClick = { onNavigateToDetail(meal.id) },
                                        onFavoriteClick = { onToggleFavorite(meal) },
                                        isFavorite = meal.isFavorite,
                                        modifier = Modifier
                                            .padding(bottom = 16.dp)
                                            .fillMaxWidth() // Grid card style for results here? Mockup uses List style for results.
                                    )
                                }
                            }

                            is UiState.Loading -> LoadingView()
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockCategories = listOf(
        com.ivansosa.recetingapp.domain.model.MealCategory("1", "Beef", "https://www.themealdb.com/images/category/beef.png", "Beef is the culinary name for meat from cattle"),
        com.ivansosa.recetingapp.domain.model.MealCategory("2", "Chicken", "https://www.themealdb.com/images/category/chicken.png", "Chicken is a type of domesticated fowl")
    )
    val mockMeals = listOf(
        MealSummary("1", "Beef Wellington", "https://www.themealdb.com/images/media/meals/vvpprx1487325699.jpg"),
        MealSummary("2", "Baked Salmon", "https://www.themealdb.com/images/media/meals/1548772327.jpg"),
        MealSummary("3", "Pancakes", "https://www.themealdb.com/images/media/meals/rwuyqx1511383174.jpg")
    )

    com.ivansosa.recetingapp.ui.theme.RecetingAppTheme {
        HomeScreenContent(
            searchState = UiState.Empty,
            categoriesState = UiState.Success(mockCategories),
            randomMealState = UiState.Empty,
            recommendedState = UiState.Success(mockMeals),
            query = "",
            onQueryChange = {},
            onGenerateRandomMeal = {},
            onClearRandomMealState = {},
            onNavigateToCategory = {},
            onNavigateToDetail = {},
            onNavigateToFavorites = {},
            onNavigateToAllCategories = {},
            onToggleFavorite = {}
        )
    }
}
