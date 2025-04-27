package com.marky.strivefit.ui.theme

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.marky.strivefit.ui.theme.CommonColors
import androidx.compose.ui.graphics.Color
enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

var LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

enum class ThemeColorOption {
    DEFAULT, ENERGETIC
}

val LocalThemeColorOption = staticCompositionLocalOf{ ThemeColorOption.DEFAULT }

class ThemeManager {
    var themeMode = mutableStateOf(ThemeMode.SYSTEM)
    var colorOption = mutableStateOf(ThemeColorOption.DEFAULT)
    fun setThemeMode(mode: ThemeMode) {
        themeMode.value = mode
    }

    fun setColorOption(option: ThemeColorOption) {
        colorOption.value = option
    }
}

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
) : androidx.compose.material3.ColorScheme {
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

private val DarkColorScheme = darkColorScheme(
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



private val LightColorScheme = lightColorScheme(
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


@Composable
fun StriveFitTheme(
    themeManager: ThemeManager = remember { ThemeManager() },
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val themeMode by remember { themeManager.themeMode }
    val colorOption by remember { themeManager.colorOption }

    val colorScheme = getColorScheme(themeMode, colorOption, dynamicColor)

    //Animate color transitions
    val animatedColorScheme = colorScheme.copy(
        primary = animateColorAsState(
            targetValue = colorScheme.primary,
            animationSpec = tween(300)
        ).value,
        surface = animateColorAsState(
            targetValue = colorScheme.surface,
            animationSpec = tween(300)
        ).value,
        secondary = animateColorAsState(
            targetValue = colorScheme.secondary,
            animationSpec = tween(300)
        ).value
    )
    CompositionLocalProvider(
       LocalThemeMode provides themeMode,
        LocalThemeColorOption provides colorOption
    ){
        MaterialTheme(
            colorScheme = colorScheme,
            typography = StriveFitTypography,
            content = content
        )
    }
}

fun getThemeIconColor( themeMode: ThemeMode): Color {
    return when (themeMode) {
        ThemeMode.LIGHT -> "#FFC107".toColor()
        ThemeMode.DARK -> "#7B68EE".toColor()
        ThemeMode.SYSTEM -> "#00BCD4".toColor()
    }
}
