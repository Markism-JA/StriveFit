package com.marky.strivefit.ui.theme

import android.app.Activity
import androidx.hilt.navigation.compose.hiltViewModel
import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.marky.strivefit.ui.viewModel.ThemeManagerViewModel

var LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

val LocalThemeColorOption = staticCompositionLocalOf{ ThemeColorOption.DEFAULT }

@Composable
fun ThemeMode.isDarkTheme(): Boolean {
    val isSystemInDark = isSystemInDarkTheme()
    return when (this) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDark
    }
}

@Composable
fun getColorScheme(
    themeMode: ThemeMode,
    colorOption: ThemeColorOption,
    dynamicColor: Boolean = false
) : ColorScheme {
    val isDarkTheme = themeMode.isDarkTheme()

    if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        return if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    return when {
        isDarkTheme && colorOption == ThemeColorOption.DEFAULT -> darkColorScheme(
            primary = DefaultDark.primary,
            primaryContainer = DefaultDark.primaryContainer,
            secondary = DefaultDark.secondary,
            background = DefaultDark.background,
            surface = DefaultDark.surface,
            onPrimary = CommonColors.onPrimary,
            surfaceVariant = DefaultDark.surfaceElevated,
            outline = CommonColors.borderDark,
            onBackground = CommonDark.onBackground,
            onSurface = CommonDark.onBgMedium,
            onSurfaceVariant = CommonDark.onBgDisabled,
            secondaryContainer = CommonColors.secondaryContainer,
            onSecondaryContainer = CommonColors.onSecondaryContainer
        )
        isDarkTheme && colorOption == ThemeColorOption.ENERGETIC -> darkColorScheme(
            primary = EnergeticDark.primary,
            primaryContainer = EnergeticDark.primaryContainer,
            secondary = EnergeticDark.secondary,
            background = EnergeticDark.background,
            surface = EnergeticDark.surface,
            onPrimary = CommonColors.onPrimary,
            surfaceVariant = EnergeticDark.surfaceElevated,
            outline = CommonColors.borderDark,
            onBackground = CommonDark.onBackground,
            onSurface = CommonDark.onBgMedium,
            onSurfaceVariant = CommonDark.onBgDisabled,
            secondaryContainer = CommonColors.secondaryContainer,
            onSecondaryContainer = CommonColors.onSecondaryContainer
        )
        !isDarkTheme && colorOption == ThemeColorOption.DEFAULT -> lightColorScheme(
            primary = DefaultLight.primary,
            primaryContainer = DefaultLight.primaryContainer,
            secondary = DefaultLight.secondary,
            background = DefaultLight.background,
            surface = DefaultLight.surface,
            onPrimary = CommonColors.onPrimary,
            surfaceVariant = DefaultLight.surfaceElevated,
            outline = CommonColors.borderLight,
            onBackground = CommonLight.onBackground,
            onSurface = CommonLight.onBgMedium,
            onSurfaceVariant = CommonLight.onBgDisabled,
            secondaryContainer = CommonColors.secondaryContainer,
            onSecondaryContainer = CommonColors.onSecondaryContainer
        )
        !isDarkTheme && colorOption == ThemeColorOption.ENERGETIC -> lightColorScheme(
            primary = EnergeticLight.primary,
            primaryContainer = EnergeticLight.primaryContainer,
            secondary = EnergeticLight.secondary,
            background = EnergeticLight.background,
            surface = EnergeticLight.surface,
            onPrimary = CommonColors.onPrimary,
            surfaceVariant = EnergeticLight.surfaceElevated,
            outline = CommonColors.borderLight,
            onBackground = CommonLight.onBackground,
            onSurface = CommonLight.onBgMedium,
            onSurfaceVariant = CommonLight.onBgDisabled,
            secondaryContainer = CommonColors.secondaryContainer,
            onSecondaryContainer = CommonColors.onSecondaryContainer
        )
        else -> lightColorScheme(
            primary = DefaultLight.primary,
            primaryContainer = DefaultLight.primaryContainer,
            secondary = DefaultLight.secondary,
            background = DefaultLight.background,
            surface = DefaultLight.surface,
            onPrimary = CommonColors.onPrimary,
            surfaceVariant = DefaultLight.surfaceElevated,
            outline = CommonColors.borderLight,
            onBackground = CommonLight.onBackground,
            onSurface = CommonLight.onBgMedium,
            onSurfaceVariant = CommonLight.onBgDisabled,
            secondaryContainer = CommonColors.secondaryContainer,
            onSecondaryContainer = CommonColors.onSecondaryContainer
        )

    }
}

val displayLarge = TextStyle(
    fontFamily = PlusJakartaSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 57.sp,
    lineHeight = 64.sp
)

val displayMedium = TextStyle(
    fontFamily = PlusJakartaSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 45.sp,
    lineHeight = 52.sp
)

val headlineLarge = TextStyle(
    fontFamily = PlusJakartaSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    lineHeight = 40.sp
)

val headlineMedium = TextStyle(
    fontFamily = PlusJakartaSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    lineHeight = 36.sp
)

val titleLarge = TextStyle(
    fontFamily = PlusJakartaSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp,
    lineHeight = 28.sp
)

val titleMedium = TextStyle(
    fontFamily = PlusJakartaSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 24.sp
)

val bodyLarge = TextStyle(
    fontFamily = DMSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp
)

val bodyMedium = TextStyle(
    fontFamily = DMSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp
)

val labelLarge = TextStyle(
    fontFamily = DMSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp
)

val labelSmall = TextStyle(
    fontFamily = DMSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp
)

val StriveFitTypography = Typography(
    displayLarge = displayLarge,
    displayMedium = displayMedium,
    headlineLarge = headlineLarge,
    headlineMedium = headlineMedium,
    titleLarge = titleLarge,
    titleMedium = titleMedium,
    bodyLarge = bodyLarge,
    bodyMedium = bodyMedium,
    labelLarge = labelLarge,
    labelSmall = labelSmall
)


@Composable
fun StriveFitTheme(
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val themeManager: ThemeManagerViewModel = hiltViewModel()
    val themeMode by themeManager.themeMode.collectAsState()
    val colorOption by themeManager.colorOption.collectAsState()
    val colorScheme = getColorScheme(themeMode, colorOption, dynamicColor)

    ApplySystemUi(themeMode)

    CompositionLocalProvider(
        LocalThemeMode provides themeMode,
        LocalThemeColorOption provides colorOption
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = StriveFitTypography,
            content = content
        )
    }
}

@Composable
fun ApplySystemUi(themeMode: ThemeMode) {
    val view = LocalView.current
    val window = (LocalActivity.current as Activity).window

    val isDarkIcons = when (themeMode){
        ThemeMode.LIGHT -> true
        ThemeMode.DARK -> false
        ThemeMode.SYSTEM -> !isSystemInDarkTheme()
    }

    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = isDarkIcons
    }
}

fun getThemeIconColor( themeMode: ThemeMode): Color {
    return when (themeMode) {
        ThemeMode.LIGHT -> "#FFC107".toColor()
        ThemeMode.DARK -> "#7B68EE".toColor()
        ThemeMode.SYSTEM -> "#00BCD4".toColor()
    }
}
