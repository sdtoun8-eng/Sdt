package dz.crepealgeria.app.three

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ThreeHeroView(
    modifier: Modifier = Modifier,
    onCustomizeClick: () -> Unit = {}
) {
    com.example.three.ThreeHeroView(
        modifier = modifier,
        onCustomizeClick = onCustomizeClick
    )
}
