package com.composetemplate.core.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.composetemplate.core.ui.theme.Blue10
import com.composetemplate.core.ui.theme.Blue20
import com.composetemplate.core.ui.theme.Blue30
import com.composetemplate.core.ui.theme.Blue40
import com.composetemplate.core.ui.theme.Blue80
import com.composetemplate.core.ui.theme.Blue90
import com.composetemplate.core.ui.theme.DarkPurpleGray10
import com.composetemplate.core.ui.theme.DarkPurpleGray90
import com.composetemplate.core.ui.theme.DarkPurpleGray99
import com.composetemplate.core.ui.theme.Orange10
import com.composetemplate.core.ui.theme.Orange20
import com.composetemplate.core.ui.theme.Orange30
import com.composetemplate.core.ui.theme.Orange40
import com.composetemplate.core.ui.theme.Orange80
import com.composetemplate.core.ui.theme.Orange90
import com.composetemplate.core.ui.theme.Purple10
import com.composetemplate.core.ui.theme.Purple20
import com.composetemplate.core.ui.theme.Purple30
import com.composetemplate.core.ui.theme.Purple40
import com.composetemplate.core.ui.theme.Purple80
import com.composetemplate.core.ui.theme.Purple90
import com.composetemplate.core.ui.theme.PurpleGray30
import com.composetemplate.core.ui.theme.PurpleGray50
import com.composetemplate.core.ui.theme.PurpleGray60
import com.composetemplate.core.ui.theme.PurpleGray80
import com.composetemplate.core.ui.theme.PurpleGray90
import com.composetemplate.core.ui.theme.Red10
import com.composetemplate.core.ui.theme.Red20
import com.composetemplate.core.ui.theme.Red30
import com.composetemplate.core.ui.theme.Red40
import com.composetemplate.core.ui.theme.Red80
import com.composetemplate.core.ui.theme.Red90

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightColors = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = DarkPurpleGray99,
    onBackground = DarkPurpleGray10,
    surface = DarkPurpleGray99,
    onSurface = DarkPurpleGray10,
    surfaceVariant = PurpleGray90,
    onSurfaceVariant = PurpleGray30,
    outline = PurpleGray50
)

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkColors = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Blue80,
    onTertiary = Blue20,
    tertiaryContainer = Blue30,
    onTertiaryContainer = Blue90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkPurpleGray10,
    onBackground = DarkPurpleGray90,
    surface = DarkPurpleGray10,
    onSurface = DarkPurpleGray90,
    surfaceVariant = PurpleGray30,
    onSurfaceVariant = PurpleGray80,
    outline = PurpleGray60
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}