package com.ivansosa.recetingapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ivansosa.recetingapp.presentation.components.BottomNavigationBar
import com.ivansosa.recetingapp.presentation.components.PrimaryFilterChip

import com.ivansosa.recetingapp.presentation.navigation.Screen
import com.ivansosa.recetingapp.presentation.viewmodel.FavoritesViewModel
import com.ivansosa.recetingapp.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onNavigateUp: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text("My Cookbook", fontWeight = FontWeight.Bold) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /* Edit mode */ }) {
                        Text("Edit", color = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = Screen.Favorites.route,
                onNavigate = { route ->
                    if (route == Screen.Home.route) onNavigateUp() // Or separate nav action
                    // Simple back/nav logic for now. 
                    // Ideally we inject standard navController actions.
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = when(val s = uiState) {
                    is UiState.Success -> "${s.data.size} recipes saved"
                    else -> "0 recipes saved"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Chips row (Mock categories for favorites)
            Row(modifier = Modifier.fillMaxWidth()) {
                PrimaryFilterChip("All Meals", true, {})
                Spacer(modifier = Modifier.width(8.dp))
                PrimaryFilterChip("Breakfast", false, {})
                Spacer(modifier = Modifier.width(8.dp))
                PrimaryFilterChip("Chicken", false, {})
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            when (val state = uiState) {
                is UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView(state.message)
                is UiState.Success -> {
                    val favorites = state.data
                    if (favorites.isEmpty()) {
                        EmptyView("No favorites yet")
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(favorites, key = { it.id }) { meal ->
                                RecipeGridCard(
                                    meal = meal,
                                    onClick = { onNavigateToDetail(meal.id) },
                                    isFavorite = true,
                                    onFavoriteClick = { viewModel.toggleFavorite(meal) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
                UiState.Empty -> EmptyView("No favorites yet")
            }
        }
    }
}
