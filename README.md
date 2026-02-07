# RecetingApp 🍳

**RecetingApp** is a modern Android application for discovering, searching, and saving your favorite cooking recipes. Built with the latest Android development technologies, including **Jetpack Compose** and **Clean Architecture**.

## ✨ Features

*   **Explore Recipes**: Discover new recipes suggested randomly or browse by categories.
*   **Search**: Find specific recipes by name.
*   **Favorites**: Save your preferred recipes locally for quick access.
*   **Full Detail**: View ingredients, step-by-step instructions, and preparation video links.
*   **Categories**: Filter recipes by food type (e.g., Breakfast, Dessert, Vegan).

## 🛠 Tech Stack

The project follows an **MVVM (Model-View-ViewModel)** architecture with **Clean Architecture** to ensure scalability and maintainability.

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetbrains/compose) (Material Design 3)
*   **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
*   **Networking**: [Retrofit](https://square.github.io/retrofit/) + [Moshi](https://github.com/square/moshi)
*   **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
*   **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
*   **Asynchrony**: Coroutines & Flow
*   **Navigation**: Navigation Compose

## 📂 Project Structure

```
com.ivansosa.recetingapp
├── data             # Repository implementations, data sources (API/DB), and mappers
├── domain           # Use cases, domain models, and repository interfaces
├── di               # Hilt modules for dependency injection
├── presentation     # UI (Screens, Components) and ViewModels
└── ui               # Theme and visual configuration
```

## 🚀 Setup & Run

1.  Clone this repository.
2.  Open the project in **Android Studio Koala** (or newer).
3.  Wait for Gradle to sync dependencies.
4.  Run the app on an emulator or physical device (Min SDK 24).

## 🔗 API

This application uses the public [TheMealDB](https://www.themealdb.com/api.php) API to fetch recipe information.
