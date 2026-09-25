package dz.crepealgeria.app.ui.theme

import androidx.compose.runtime.Composable

@Composable
fun CrepeAlgeriaTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    com.example.ui.theme.CrepeAlgeriaTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}
