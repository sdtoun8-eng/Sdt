package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CrepeGoldLight,
    onPrimary = ChocoBrownDark,
    primaryContainer = CrepeGoldDark,
    onPrimaryContainer = Color(0xFFFFDDB8),
    secondary = Color(0xFFE6BE8A),
    onSecondary = ChocoBrownDark,
    secondaryContainer = ChocoBrown,
    onSecondaryContainer = Color(0xFFF0E5DE),
    tertiary = BerryRedLight,
    onTertiary = Color(0xFF4A0004),
    tertiaryContainer = Color(0xFF8B0014),
    onTertiaryContainer = Color(0xFFFFDAD8),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = CrepeGold,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE0B8),
    onPrimaryContainer = ChocoBrownDark,
    secondary = ChocoBrown,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEFE6DE),
    onSecondaryContainer = ChocoBrownDark,
    tertiary = BerryRed,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDAD8),
    onTertiaryContainer = Color(0xFF410006),
    background = CreamBackground,
    onBackground = Color(0xFF231B15),
    surface = CreamSurface,
    onSurface = Color(0xFF231B15),
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = Color(0xFF55443B)
)

@Composable
fun CrepeAlgeriaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep signature warm crepe branding consistent
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = CrepeAlgeriaTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
