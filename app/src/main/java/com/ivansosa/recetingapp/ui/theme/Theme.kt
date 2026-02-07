package com.ivansosa.recetingapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    tertiary = TextGray,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextBlack,
    onSurface = TextBlack,
    surfaceVariant = SurfaceGray,
    onSurfaceVariant = TextGray
)

private val DarkColorScheme = lightColorScheme( // Forcing light theme structure even in dark mode for this specific design request for now, or could map to dark equivalents
    primary = PrimaryGreen,
    secondary = SecondaryGreen,
    tertiary = TextGray,
    background = TextBlack, // innovative dark mode mapping
    surface = TextBlack,
    onPrimary = TextBlack,
    onSecondary = TextBlack,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun RecetingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        //     val context = LocalContext.current
        //     if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        // }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}