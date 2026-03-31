<div align="center">

# CookingSoul

**A modern Android recipe discovery app built with Jetpack Compose and Clean Architecture**

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min_SDK-24-green?style=for-the-badge)
![Target SDK](https://img.shields.io/badge/Target_SDK-34-blue?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-1.0-orange?style=for-the-badge)

</div>

---

## Overview

**CookingSoul** is a feature-rich Android application for discovering, searching, and saving cooking recipes. It integrates with the free [TheMealDB](https://www.themealdb.com/api.php) public API and stores favorites locally with Room. The project demonstrates production-grade architecture patterns: Clean Architecture, MVVM, Hilt DI, and Jetpack Compose with Material Design 3.

---

## Screenshots

> Add screenshots here once the app is running on a device.

---

## Features

| Feature | Description |
|---------|-------------|
| **Home & Discovery** | Curated recipe categories and recommended meals on launch |
| **Search** | Real-time search by recipe name with empty/error state handling |
| **Categories** | Browse and filter all available meal categories |
| **Meal Detail** | Full recipe with image, ingredients, step-by-step instructions, and YouTube link |
| **Favorites** | Save and manage recipes locally; persisted across sessions |
| **Random Meal** | Discover a random recipe with one tap |
| **Share** | Share any recipe via Android native share intent |

---

## Architecture

The project follows **Clean Architecture** with an **MVVM** presentation pattern, divided into three layers:

```
com.ivansosa.recetingapp
├── data/
│   ├── db/              # Room entities, DAOs, and AppDatabase
│   ├── network/         # Retrofit API service and DTOs
│   ├── repository/      # MealsRepositoryImpl (combines API + local DB)
│   └── Mappers.kt       # DTO → Domain model transformations
│
├── domain/
│   ├── model/           # Pure Kotlin domain models
│   ├── repository/      # MealsRepository interface
│   └── usecase/         # One use case per business operation
│
├── presentation/
│   ├── screens/         # Composable screens and UI components
│   ├── viewmodel/       # Hilt ViewModels + UiState sealed interface
│   ├── components/      # Reusable Composables (BottomNav, SearchBar, etc.)
│   └── navigation/      # NavGraph with typed routes
│
├── di/                  # Hilt modules (Network, Database, Repository, Dispatchers)
├── ui/                  # Material 3 theme, colors, typography
└── CookingSoulApplication  # Application class
```

### Data Flow

```
UI (Composable)
    ↕ observes StateFlow
ViewModel
    ↕ calls suspend fun / Flow
UseCase
    ↕ calls interface methods
MealsRepository (interface)
    ↕ implemented by
MealsRepositoryImpl
    ├── TheMealDbApi  (Retrofit → TheMealDB REST API)
    └── FavoriteDao   (Room → local SQLite)
```

---

## Tech Stack

| Layer | Library | Version |
|-------|---------|---------|
| Language | Kotlin | 2.0.21 |
| UI | Jetpack Compose (Material 3) | BOM 2024.02.01 |
| DI | Hilt | 2.51.1 |
| Networking | Retrofit + OkHttp | 2.9.0 / 4.12.0 |
| Serialization | Moshi + Kotlin Codegen | 1.15.1 |
| Local DB | Room | 2.6.1 |
| Image Loading | Coil | 2.6.0 |
| Async | Coroutines + Flow | - |
| Navigation | Navigation Compose | 2.7.7 |
| Build | Android Gradle Plugin | 8.13.2 |
| Java Target | Java 17 | - |

**Testing:**

| Library | Purpose |
|---------|---------|
| JUnit 4 | Unit testing framework |
| Mockito + mockito-kotlin | Mocking dependencies |
| Turbine | Flow testing |
| kotlinx-coroutines-test | Coroutine testing utilities |
| Espresso | Instrumented UI tests |

---

## Screens & Navigation

```
SplashScreen
    └── HomeScreen
            ├── MealDetailScreen
            ├── FavoritesScreen
            └── CategoriesScreen
                    └── CategoryMealsScreen
                            └── MealDetailScreen
```

| Screen | Route | Description |
|--------|-------|-------------|
| `SplashScreen` | `splash` | Animated launch screen with timed redirect |
| `HomeScreen` | `home` | Search, category chips, recommended meals |
| `CategoriesScreen` | `categories_list` | Full grid of all categories |
| `CategoryMealsScreen` | `category/{categoryName}` | Meals filtered by selected category |
| `MealDetailScreen` | `detail/{mealId}` | Full recipe detail with FAB favorite toggle |
| `FavoritesScreen` | `favorites` | Saved recipes from local Room database |

---

## Domain Layer: Use Cases

| Use Case | Description |
|----------|-------------|
| `SearchMealsUseCase` | Search recipes by name via API |
| `GetCategoriesUseCase` | Fetch all meal categories |
| `GetMealsByCategoryUseCase` | Load meals filtered by category |
| `GetMealDetailUseCase` | Fetch full recipe detail + random meal |
| `ObserveFavoritesUseCase` | Observe favorites in real-time (Flow) |
| `ToggleFavoriteUseCase` | Add or remove a recipe from favorites |
| `DeleteFavoriteUseCase` | Remove a favorite entry by ID |

---

## Data Layer

### Remote: TheMealDB API

**Base URL:** `https://www.themealdb.com/api/json/v1/1/`

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/search.php?s={query}` | GET | Search meals by name |
| `/categories.php` | GET | Fetch all categories |
| `/filter.php?c={category}` | GET | Filter meals by category |
| `/lookup.php?i={id}` | GET | Fetch full meal detail |
| `/random.php` | GET | Fetch a random meal |

### Local: Room Database

**Entity:** `FavoriteMealEntity`

| Column | Type | Description |
|--------|------|-------------|
| `id` | String (PK) | TheMealDB meal ID |
| `name` | String | Meal name |
| `thumbUrl` | String? | Thumbnail image URL |
| `tags` | String? | Comma-separated tags |
| `category` | String? | Category name |
| `area` | String? | Geographic origin |
| `savedAt` | Long | Timestamp of when saved |

---

## ViewModels & State

All ViewModels expose state via `StateFlow<UiState<T>>`:

```kotlin
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Empty : UiState<Nothing>
}
```

| ViewModel | Screens | Key States |
|-----------|---------|------------|
| `HomeViewModel` | Home | searchState, categoriesState, recommendedState, randomMealState |
| `CategoryMealsViewModel` | CategoryMeals | uiState |
| `MealDetailViewModel` | MealDetail | uiState (with SavedStateHandle) |
| `FavoritesViewModel` | Favorites | uiState (real-time Flow from Room) |

---

## Setup & Installation

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 17
- Android device or emulator running API 24+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/ivansosa/CookingSoul.git
   cd CookingSoul
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select **File > Open** and navigate to the project folder
   - Wait for Gradle to sync

3. **Run the app**
   - Select a device or emulator (API 24+)
   - Press **Run** or use `Shift + F10`

> The app uses the free TheMealDB API — no API key is required.

---

## Pre-Release Checklist

Before publishing to the Google Play Store, make sure the following are addressed:

- [ ] Enable `isMinifyEnabled = true` in `app/build.gradle.kts`
- [ ] Configure ProGuard rules for Moshi and Retrofit in `proguard-rules.pro`
- [ ] Move all hardcoded strings to `strings.xml` for localization support
- [ ] Fix dark mode theme (currently uses light color scheme for dark mode)
- [ ] Disable `HttpLoggingInterceptor` in release builds
- [ ] Add Firebase Crashlytics for crash reporting
- [ ] Review and configure `backup_rules.xml` and `data_extraction_rules.xml`

---

## Known Limitations

- **No API caching:** Every request hits the network; Room is only used for favorites.
- **No pagination:** Search and category results load all items at once.
- **Category detail limit:** Only the first 20 meals per category load with full details.
- **Favorites are local only:** No cloud sync or user account system.
- **Dark mode:** The app forces the light color scheme in dark mode (cosmetic issue).
- **Hardcoded recommended search:** Home screen recommended meals always search for "Chicken".

---

## Testing

Run unit tests:

```bash
./gradlew test
```

Run instrumented tests (requires a connected device or emulator):

```bash
./gradlew connectedAndroidTest
```

Current test coverage is minimal. The following areas have test coverage:

- `HomeViewModel` — search state transitions (success / empty)
- `Mappers` — DTO to domain model mapping

---

## Project Configuration

| Setting | Value |
|---------|-------|
| `applicationId` | `com.ivansosa.recetingapp` |
| `minSdk` | 24 (Android 7.0 Nougat) |
| `targetSdk` | 34 (Android 14) |
| `compileSdk` | 34 |
| `versionCode` | 1 |
| `versionName` | 1.0 |
| Java compatibility | Java 17 |
| Kotlin | 2.0.21 |

---

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m "feat: add your feature"`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## API Reference

This app is powered by the free and open [TheMealDB API](https://www.themealdb.com/api.php).

- No authentication required for the free tier
- Rate limits apply — avoid bulk parallel requests
- Data includes meal names, images, ingredients, measures, and instructions

---

## License

```
Copyright 2024 Ivan Sosa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---

<div align="center">

Built with Kotlin and Jetpack Compose

</div>
