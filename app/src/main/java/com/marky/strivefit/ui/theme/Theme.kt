package com.marky.strivefit.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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

//Typography
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
)


@Composable
fun StriveFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
      typography = StriveFitTypography,
      content = content
    )
}